package fintech.usecase.money;

import fintech.api.msg.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoneyUseCaseTest extends AMoneyTest {

    @Test
    public void transferUseCase() {
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

}
