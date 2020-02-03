package fintech.domain.account;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import fintech.domain.account.AccountImpl.NegativeWithdrawMoneyException;
import fintech.domain.account.AccountImpl.NotEnoughMoneyException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)
public class AccountImplTest {

    @Test
    @UseDataProvider("getMoneyDP")
    public void getMoney(long money) {
        AccountImpl account = new AccountImpl(money);
        assertThat(account.getMoney(), is(money));
    }

    @Test
    @UseDataProvider("depositForcedDP")
    public void depositForced(long startMoney, long depositMoney, long resultMoney) {
        AccountImpl account = new AccountImpl(startMoney);
        long currentMoney = account.depositForced(depositMoney);
        assertThat(currentMoney, is(resultMoney));
        assertThat(account.getMoney(), is(resultMoney));
    }

    @Test(expected = ArithmeticException.class)
    @UseDataProvider("depositForcedNegativeDP")
    public void depositForcedNegative(long startMoney, long depositMoney) {
        AccountImpl account = new AccountImpl(startMoney);
        account.depositForced(depositMoney);
    }

    @Test
    @UseDataProvider("withdrawDP")
    public void withdraw(long startMoney, long withdrawMoney, long resultMoney) {
        AccountImpl account = new AccountImpl(startMoney);
        long currentMoney = account.withdraw(withdrawMoney);
        assertThat(currentMoney, is(resultMoney));
        assertThat(account.getMoney(), is(resultMoney));
    }

    @Test(expected = NegativeWithdrawMoneyException.class)
    @UseDataProvider("withdrawNegativeDP")
    public void withdrawNegative(long withdrawMoney) {
        AccountImpl account = new AccountImpl(666L);
        account.withdraw(withdrawMoney);
    }

    @Test(expected = NotEnoughMoneyException.class)
    @UseDataProvider("withdrawNotEnoughNegativeDP")
    public void withdrawNotEnoughNegative(long startMoney, long withdrawMoney) {
        AccountImpl account = new AccountImpl(startMoney);
        account.withdraw(withdrawMoney);
    }

    @Test
    public void copy() {
        long startMoney = 0L;
        long depositMoney = 1L;
        AccountImpl account = new AccountImpl(startMoney);
        AccountImpl accountCopy = account.copy();
        long resultMoney = accountCopy.depositForced(depositMoney);
        assertThat(resultMoney, is(depositMoney));
        assertThat(account.getMoney(), is(startMoney));

    }

    @DataProvider
    public static List<List<Object>> getMoneyDP() {
        return List.of(
                List.of(0),
                List.of(1),
                List.of(-1),
                List.of(Long.MAX_VALUE),
                List.of(Long.MIN_VALUE)
        );
    }

    @DataProvider
    public static List<List<Object>> depositForcedDP() {
        return List.of(
                List.of(0, 0, 0),
                List.of(1, -1, 0),
                List.of(-1, 1, 0),
                List.of(Long.MIN_VALUE / 2, Long.MIN_VALUE / 2, Long.MIN_VALUE),
                List.of(Long.MAX_VALUE, Long.MIN_VALUE, -1),
                List.of(Long.MAX_VALUE, 666 - Long.MAX_VALUE, 666)
        );
    }

    @DataProvider
    public static List<List<Object>> depositForcedNegativeDP() {
        return List.of(
                List.of(Long.MIN_VALUE / 2, Long.MIN_VALUE / 2 - 1),
                List.of(Long.MIN_VALUE, Long.MIN_VALUE),
                List.of(Long.MIN_VALUE, -1),
                List.of(Long.MAX_VALUE, Long.MAX_VALUE),
                List.of(Long.MAX_VALUE, 1)
        );
    }

    @DataProvider
    public static List<List<Object>> withdrawDP() {
        return List.of(
                List.of(1, 1, 0),
                List.of(Long.MAX_VALUE, 1, Long.MAX_VALUE - 1),
                List.of(Long.MAX_VALUE, Long.MAX_VALUE, 0),
                List.of(Long.MAX_VALUE, (Long.MAX_VALUE - 1) / 2, (Long.MAX_VALUE - 1) / 2 + 1)
        );
    }

    @DataProvider
    public static List<List<Object>> withdrawNegativeDP() {
        return List.of(
                List.of(0),
                List.of(Long.MIN_VALUE),
                List.of(-1)
        );
    }

    @DataProvider
    public static List<List<Object>> withdrawNotEnoughNegativeDP() {
        return List.of(
                List.of(1, 2),
                List.of(2, 3),
                List.of(Long.MAX_VALUE - 1, Long.MAX_VALUE)
        );
    }

}
