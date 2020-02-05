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
        return money = Math.addExact(money, moneyAmount);
    }

    @Override
    public long withdraw(long moneyAmount) {
        if (moneyAmount <= 0) {
            throw new NegativeWithdrawMoneyException();
        }
        if (money < moneyAmount) {
            throw new NotEnoughMoneyException();
        }
        return money = Math.subtractExact(money, moneyAmount);
    }

    public AccountImpl copy() {
        return new AccountImpl(money);
    }

    public static class NotEnoughMoneyException extends RuntimeException {
    }

    public static class NegativeWithdrawMoneyException extends RuntimeException {
    }
}
