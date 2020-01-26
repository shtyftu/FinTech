package fintech.domain.account;

public interface Account {

    long getMoney();

    void depositForced(long moneyAmount);

    void withdraw(long moneyAmount);
}
