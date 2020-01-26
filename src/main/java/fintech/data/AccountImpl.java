package fintech.data;

import fintech.domain.account.Account;

public class AccountImpl implements Account {

    public int money;

    public AccountImpl(int money) {
        this.money = money;
    }

    @Override
    public long getMoney() {
        return money;
    }

    @Override
    public void depositForced(long moneyAmount) {
        money += moneyAmount;
    }

    @Override
    public void withdraw(long moneyAmount) {
        if (money < moneyAmount) {
            throw new AccountRepositoryImpl.NotEnoughMoneyException();
        }
        money -= moneyAmount;
    }

    public AccountImpl copy() {
        return new AccountImpl(money);
    }
}
