package org.abf.hobt.service.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import org.abf.hobt.service.ice.IDataTransferObject;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Custom Writer and Reader for classes that extend {@link IDataTransferObject} using GSON for JSON conversion
 *
 * @author Hector Plahar
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DataTransferObjectJSONHandler
    implements MessageBodyWriter<IDataTransferObject>, MessageBodyReader<IDataTransferObject> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(IDataTransferObject data, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(IDataTransferObject data, Class<?> type, Type genericType, Annotation[] annotations, MediaType
        mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
        throws IOException, WebApplicationException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer out = new OutputStreamWriter(entityStream)) {
            gson.toJson(data, out);
        }
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public IDataTransferObject readFrom(Class<IDataTransferObject> type, Type genericType, Annotation[] annotations,
                                        MediaType mediaType,
                                        MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
        throws IOException, WebApplicationException {
        Gson gson = new GsonBuilder().create();
        try (Reader in = new InputStreamReader(entityStream)) {
            return gson.fromJson(in, type);
        }
    }
}
