package fintech;

import fintech.infra.ABinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ext.Provider;

@Provider
class JerseyApp extends ResourceConfig {
    public JerseyApp() {
        register(new AppBinder());
        packages("fintech.api");
    }

    static class AppBinder extends ABinder {
    }
}
