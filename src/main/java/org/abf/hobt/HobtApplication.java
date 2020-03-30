package org.abf.hobt;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class HobtApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(MultiPartFeature.class);
        return classes;
    }
}
