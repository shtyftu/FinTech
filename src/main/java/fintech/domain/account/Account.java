package fintech.domain.account;

public interface Account {

    long getMoney();

    long depositForced(long moneyAmount);

    long withdraw(long moneyAmount);
}
