/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.simplepush.server;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.aerogear.simplepush.protocol.Handshake;
import org.jboss.aerogear.simplepush.protocol.HandshakeResponse;
import org.jboss.aerogear.simplepush.protocol.Register;
import org.jboss.aerogear.simplepush.protocol.RegisterResponse;
import org.jboss.aerogear.simplepush.protocol.Status;
import org.jboss.aerogear.simplepush.protocol.impl.HandshakeResponseImpl;
import org.jboss.aerogear.simplepush.protocol.impl.RegisterResponseImpl;
import org.jboss.aerogear.simplepush.protocol.impl.StatusImpl;

public class SimplePushServer {
    
    private final Set<Channel> channels = Collections.newSetFromMap(new ConcurrentHashMap<Channel, Boolean>());
    
    public HandshakeResponse handleHandshake(final Handshake handshake) {
        final Set<Channel> channels = new HashSet<Channel>();
        for (String channelId : handshake.getChannelIds()) {
            channels.add(new DefaultChannel(handshake.getUAID(), channelId, defaultEndpoint(channelId)));
        }
        return new HandshakeResponseImpl(handshake.getUAID());
    }
    
    public RegisterResponse handleRegister(final Register register, final UUID uaid) {
        final String channelId = register.getChannelId();
        final String pushEndpoint = defaultEndpoint(channelId);
        boolean added = channels.add(new DefaultChannel(uaid, channelId, pushEndpoint));
        final Status status = added ? new StatusImpl(200, "OK") : new StatusImpl(409, "Conflict: channeld [" + channelId + " is already in use");
        return new RegisterResponseImpl(channelId, status, pushEndpoint);
    }
    
    public Channel getChannel(final String channelId) {
        for (Channel channel : channels) {
            if (channel.getChannelId().equals(channelId)) {
                return channel;
            }
        }
        throw new RuntimeException("Could not find a channel with id [" + channelId + "]");
    }
    
    public boolean removeChannel(final String channnelId) {
        return channels.remove(getChannel(channnelId));
    }
    
    private String defaultEndpoint(final String channelId) {
        return "/endpoint/" + channelId;
    }

}
