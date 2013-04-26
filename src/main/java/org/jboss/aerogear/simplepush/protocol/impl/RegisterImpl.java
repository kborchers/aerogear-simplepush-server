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
package org.jboss.aerogear.simplepush.protocol.impl;

import static org.jboss.aerogear.simplepush.util.ArgumentUtil.checkNotNull;

import org.jboss.aerogear.simplepush.protocol.Register;

public class RegisterImpl implements Register {
    
    private final String channelId;

    public RegisterImpl(final String channelId) {
        checkNotNull(channelId, "channelId");
        this.channelId = channelId;
    }

    @Override
    public Type getMessageType() {
        return Type.REGISTER;
    }

    @Override
    public String getChannelId() {
        return channelId;
    }
    
    @Override 
    public String toString() {
        return "RegisterImpl[messageType=" + getMessageType() + ", channelId=" + channelId + "]";
    }
    
}