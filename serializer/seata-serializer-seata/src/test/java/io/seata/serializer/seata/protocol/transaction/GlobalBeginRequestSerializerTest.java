/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.seata.serializer.seata.protocol.transaction;

import io.seata.serializer.seata.protocol.v1.SeataV1Serializer;
import io.seata.core.protocol.transaction.GlobalBeginRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Global begin request codec test.
 *
 * @author zhangsen
 */
public class GlobalBeginRequestSerializerTest {

    /**
     * The Seata codec.
     */
    SeataV1Serializer seataSerializer = new SeataV1Serializer();

    /**
     * Test codec.
     */
    @Test
    public void test_codec(){
        GlobalBeginRequest globalBeginRequest = new GlobalBeginRequest();
        globalBeginRequest.setTimeout(10);
        globalBeginRequest.setTransactionName("a24");

        byte[] bytes = seataSerializer.serialize(globalBeginRequest);

        GlobalBeginRequest globalBeginRequest2 = seataSerializer.deserialize(bytes);
        assertThat(globalBeginRequest2.getTransactionName()).isEqualTo(globalBeginRequest.getTransactionName());
        assertThat(globalBeginRequest2.getTimeout()).isEqualTo(globalBeginRequest.getTimeout());
    }

}
