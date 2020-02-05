package fintech.usecase.money;

import fintech.domain.account.AccountImpl;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MoneyUseCaseNegativeTest extends AMoneyTest {

    @Test
    public void notEnoughMoney(){
        // Given
        String accountId0 = "0";
        String accountId1 = "1";
        int transferAmount = 10;

        // When
        Response response = transferRaw(accountId0, accountId1, transferAmount);

        // Then
        assertNotNull(response);
        assertThat(response.getStatus(), is(HttpURLConnection.HTTP_CONFLICT));
        assertThat(response.readEntity(String.class), is(AccountImpl.NotEnoughMoneyException.class.getSimpleName()));
    }

}
