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

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ProtobufMessageException extends WebApplicationException {
    public ProtobufMessageException(Throwable cause) {
        super(cause);
    }

    @Override
    public Response getResponse() {
        return Response.status(Response.Status.BAD_REQUEST).entity(getCause().getMessage()).type("text/plain").build();
    }
}
