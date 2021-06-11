package org.abf.hobt.service.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.abf.hobt.service.ice.IDataTransferObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author Hector Plahar
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArrayDataJSONHandler implements MessageBodyWriter<ArrayList<IDataTransferObject>>,
    MessageBodyReader<ArrayList<IDataTransferObject>> {
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public ArrayList<IDataTransferObject> readFrom(Class<ArrayList<IDataTransferObject>> type, Type genericType,
                                                   Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
                                                   InputStream entityStream)
        throws IOException, WebApplicationException {
        Gson gson = new GsonBuilder().create();
        try (Reader in = new InputStreamReader(entityStream)) {
            return gson.fromJson(in, type);
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(ArrayList<IDataTransferObject> iDataTransferModels, Class<?> type, Type genericType, Annotation[]
        annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(ArrayList<IDataTransferObject> data, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
        throws IOException, WebApplicationException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer out = new OutputStreamWriter(entityStream)) {
            gson.toJson(data, out);
        }
    }
}
