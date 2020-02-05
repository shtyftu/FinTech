package fintech.infra;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static java.net.HttpURLConnection.HTTP_CONFLICT;

public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException e) {
        return Response.status(HTTP_CONFLICT).entity(e.getClass().getSimpleName()).build();
    }
}
