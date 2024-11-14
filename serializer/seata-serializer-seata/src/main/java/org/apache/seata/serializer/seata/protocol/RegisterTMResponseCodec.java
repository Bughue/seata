/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.seata.serializer.seata.protocol;


import io.netty.buffer.ByteBuf;
import org.apache.seata.core.protocol.AbstractIdentifyResponse;
import org.apache.seata.core.protocol.RegisterTMResponse;
import org.apache.seata.serializer.seata.MessageSeataCodec;
import org.apache.seata.serializer.seata.MultiVersionCodec;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

/**
 * The type Register tm response codec.
 *
 */
public class RegisterTMResponseCodec extends AbstractIdentifyResponseCodec implements MultiVersionCodec {

    @Override
    public Class<?> getMessageClassType() {
        return RegisterTMResponse.class;
    }

    @Override
    public Map<MultiVersionCodec.VersionRange, MessageSeataCodec> oldVersionCodec() {
        return Collections.singletonMap(
                // todo  【我的todo】
                new MultiVersionCodec.VersionRange("2.1.0"),
                new MessageSeataCodec() {
                    @Override
                    public Class<?> getMessageClassType() {
                        return RegisterTMResponseCodec.this.getMessageClassType();
                    }

                    @Override
                    public <T> void encode(T t, ByteBuf out) {
                        AbstractIdentifyResponse abstractIdentifyResponse = (AbstractIdentifyResponse)t;
                        boolean identified = abstractIdentifyResponse.isIdentified();
                        String version = abstractIdentifyResponse.getVersion();

                        out.writeByte(identified ? (byte)1 : (byte)0);
                        if (version != null) {
                            byte[] bs = version.getBytes(UTF8);
                            out.writeShort((short)bs.length);
                            if (bs.length > 0) {
                                out.writeBytes(bs);
                            }
                        } else {
                            out.writeShort((short)0);
                        }
                    }

                    @Override
                    public <T> void decode(T t, ByteBuffer in) {
                        AbstractIdentifyResponse abstractIdentifyResponse = (AbstractIdentifyResponse)t;

                        abstractIdentifyResponse.setIdentified(in.get() == 1);
                        short len = in.getShort();
                        if (len <= 0) {
                            return;
                        }
                        if (in.remaining() < len) {
                            return;
                        }
                        byte[] bs = new byte[len];
                        in.get(bs);
                        abstractIdentifyResponse.setVersion(new String(bs, UTF8));
                    }
                }
        );
    }
}
