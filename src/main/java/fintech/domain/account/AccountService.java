package fintech.domain.account;


import fintech.domain.common.UnitOfWork;

public interface AccountService {

    long getState(AccountId accountId, UnitOfWork uow);

    void deposit(AccountId accountId, long moneyAmount, UnitOfWork uow);

    void transfer(AccountId senderId, AccountId receiverId, long moneyAmount, UnitOfWork uow);

}
