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

import io.seata.serializer.seata.SeataSerializer;
import io.seata.core.protocol.ProtocolConstants;
import io.seata.core.protocol.transaction.GlobalCommitRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Global commit request codec test.
 *
 * @author zhangsen
 */
public class GlobalCommitRequestCodecTest {

    /**
     * The Seata codec.
     */
    SeataSerializer seataSerializer = new SeataSerializer(ProtocolConstants.VERSION_1);

    /**
     * Test codec.
     */
    @Test
    public void test_codec(){
        GlobalCommitRequest globalCommitRequest = new GlobalCommitRequest();
        globalCommitRequest.setExtraData("aaaa");
        globalCommitRequest.setXid("adf");

        byte[] bytes = seataSerializer.serialize(globalCommitRequest);

        GlobalCommitRequest globalCommitRequest2 = seataSerializer.deserialize(bytes);

        assertThat(globalCommitRequest2.getExtraData()).isEqualTo(globalCommitRequest.getExtraData());
        assertThat(globalCommitRequest2.getXid()).isEqualTo(globalCommitRequest.getXid());

    }


}
