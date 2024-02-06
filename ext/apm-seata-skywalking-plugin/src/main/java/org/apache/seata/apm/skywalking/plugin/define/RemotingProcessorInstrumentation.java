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
package org.apache.seata.apm.skywalking.plugin.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.apache.skywalking.apm.agent.core.plugin.match.MultiClassNameMatch.byMultiClassMatch;


public class RemotingProcessorInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {

    private static final String INTERCEPTOR_CLASS = "org.apache.seata.apm.skywalking.plugin.RemotingProcessorProcessInterceptor";

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[0];
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
            new InstanceMethodsInterceptPoint() {
                @Override
                public ElementMatcher<MethodDescription> getMethodsMatcher() {
                    return named("process");
                }

                @Override
                public String getMethodsInterceptor() {
                    return INTERCEPTOR_CLASS;
                }

                @Override
                public boolean isOverrideArgs() {
                    return false;
                }
            }
        };
    }

    @Override
    protected ClassMatch enhanceClass() {
        return byMultiClassMatch("org.apache.seata.core.rpc.processor.client.ClientHeartbeatProcessor",
            "org.apache.seata.core.rpc.processor.client.ClientOnResponseProcessor",
            "org.apache.seata.core.rpc.processor.client.RmBranchCommitProcessor",
            "org.apache.seata.core.rpc.processor.client.RmBranchRollbackProcessor",
            "org.apache.seata.core.rpc.processor.client.RmUndoLogProcessor",
            "org.apache.seata.core.rpc.processor.server.RegRmProcessor",
            "org.apache.seata.core.rpc.processor.server.RegTmProcessor",
            "org.apache.seata.core.rpc.processor.server.ServerHeartbeatProcessor",
            "org.apache.seata.core.rpc.processor.server.ServerOnRequestProcessor",
            "org.apache.seata.core.rpc.processor.server.ServerOnResponseProcessor");
    }
}
