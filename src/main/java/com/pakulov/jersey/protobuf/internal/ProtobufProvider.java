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

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Message;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Provider
@Produces(MediaTypeExt.APPLICATION_PROTOBUF)
@Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
public class ProtobufProvider implements MessageBodyReader<Message>, MessageBodyWriter<Message> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isAssignableFrom(type);
    }

    @Override
    public Message readFrom(Class<Message> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        try {
            Method newBuilder = type.getMethod("newBuilder");
            AbstractMessage.Builder<?> messageBuilder = (AbstractMessage.Builder<?>) newBuilder.invoke(type);
            return messageBuilder.mergeFrom(entityStream).build();
        } catch (Exception e) {
            throw new ProtobufMessageException(e);
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isAssignableFrom(type);
    }

    @Override
    public long getSize(Message message, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return message.getSerializedSize();
    }

    @Override
    public void writeTo(Message message, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        try {
            entityStream.write(message.toByteArray());
        } catch (Exception e) {
            throw new ProtobufMessageException(e);
        }
    }

    private boolean isAssignableFrom(Class<?> type) {
        return Message.class.isAssignableFrom(type);
    }
}
