package fintech;

import fintech.infra.AppBinder;
import fintech.infra.RuntimeExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ext.Provider;

@Provider
public class JerseyApp extends ResourceConfig {
    public JerseyApp() {
        register(new AppBinder());
        register(RuntimeExceptionMapper.class);
        packages("fintech.api");
    }

}
