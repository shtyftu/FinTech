package fintech.usecase.money;

import fintech.api.MoneyResourceImpl;
import fintech.api.msg.*;
import fintech.infra.RuntimeExceptionMapper;
import fintech.infra.TestBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class AMoneyTest extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig(MoneyResourceImpl.class);
        resourceConfig.register(new TestBinder());
        resourceConfig.register(RuntimeExceptionMapper.class);
        resourceConfig.packages("fintech.api");
        return resourceConfig;
    }

    protected DepositResponse deposit(String accountId, long moneyAmount) {
        return post("/money/deposit", new DepositRequest(accountId, moneyAmount), DepositResponse.class);
    }

    protected GetStateResponse state(String accountId) {
        return post("/money/state", new GetStateRequest(accountId), GetStateResponse.class);
    }

    protected TransferResponse transfer(String senderId, String receiverId, long transferAmount) {
        return post(
                "/money/transfer",
                new TransferRequest(senderId, receiverId, transferAmount),
                TransferResponse.class
        );
    }

    protected Response transferRaw(String senderId, String receiverId, long transferAmount) {
        return post("/money/transfer", new TransferRequest(senderId, receiverId, transferAmount));
    }

    private Response post(@SuppressWarnings("SameParameterValue") String path, Object entity) {
        return target(path).request().post(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE));
    }

    private <T> T post(String path, Object entity, Class<T> clazz) {
        return target(path).request().post(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE), clazz);
    }
}
