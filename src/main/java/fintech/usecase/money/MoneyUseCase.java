package fintech.usecase.money;


import fintech.domain.account.AccountId;

public interface MoneyUseCase {

    long getState(AccountId accountId);

    void deposit(AccountId accountId, long moneyAmount);

    void transfer(AccountId senderId, AccountId receiverId, long moneyAmount);
}
