package fintech.domain.account;

public class AccountImpl implements Account {

    public long money;

    public AccountImpl(long money) {
        this.money = money;
    }

    @Override
    public long getMoney() {
        return money;
    }

    @Override
    public long depositForced(long moneyAmount) {
        money += moneyAmount;
        return money;
    }

    @Override
    public long withdraw(long moneyAmount) {
        if (money < moneyAmount) {
            throw new NotEnoughMoneyException();
        }
        money -= moneyAmount;
        return money;
    }

    public AccountImpl copy() {
        return new AccountImpl(money);
    }

    private static class NotEnoughMoneyException extends RuntimeException {
    }
}
