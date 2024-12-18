/**
 * Copyright 2014 Alexander Pakulov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pakulov.jersey.protobuf.internal;

import com.pakulov.jersey.protobuf.ProtobufFeature;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.FeatureContext;

@Priority(Priorities.ENTITY_CODER)
public class ProtobufAutoDiscoverable implements AutoDiscoverable {

    @Override
    public void configure(FeatureContext context) {
        if (!context.getConfiguration().isRegistered(ProtobufFeature.class)) {
            context.register(ProtobufFeature.class);
        }
    }
}
