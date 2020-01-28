package fintech.usecase.money;


import fintech.domain.account.AccountId;

public interface MoneyUseCase {

    long getState(AccountId accountId);

    long deposit(AccountId accountId, long moneyAmount);

    long transfer(AccountId senderId, AccountId receiverId, long moneyAmount);
}
