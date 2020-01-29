package fintech;

import fintech.api.MoneyResourceImpl;
import fintech.api.msg.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class MoneyIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig(MoneyResourceImpl.class);
        resourceConfig.register(new JerseyApp.Binder());
        resourceConfig.packages("fintech.api");
        return resourceConfig;
    }

    @Test
    public void testTransferUseCase() {
        String accountId0 = "0";
        String accountId1 = "1";
        long moneyAmount = 100L;
        long transferAmount = 30L;

        DepositResponse depositResponse = deposit(accountId0, moneyAmount);
        assertEquals(depositResponse.currentMoney, moneyAmount);

        GetStateResponse stateResponse0 = state(accountId0);
        assertEquals(stateResponse0.currentMoney, moneyAmount);

        TransferResponse transferResponse = transfer(accountId0, accountId1, transferAmount);
        assertEquals(transferResponse.senderMoney, moneyAmount - transferAmount);

        GetStateResponse stateResponse1 = state(accountId1);
        assertEquals(stateResponse1.currentMoney, transferAmount);
    }

    private DepositResponse deposit(String accountId, long moneyAmount) {
        return post("/money/deposit", new DepositRequest(accountId, moneyAmount), DepositResponse.class);
    }

    private GetStateResponse state(String accountId) {
        return post("/money/state", new GetStateRequest(accountId), GetStateResponse.class);
    }

    private TransferResponse transfer(String senderId, String receiverId, long transferAmount) {
        return post(
                "/money/transfer",
                new TransferRequest(senderId, receiverId, transferAmount),
                TransferResponse.class
        );
    }

    private <T> T post(String path, Object entity, Class<T> clazz) {
        return target(path).request().post(Entity.entity(entity, MediaType.APPLICATION_JSON_TYPE), clazz);
    }
}
