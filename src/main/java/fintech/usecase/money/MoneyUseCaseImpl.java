package fintech.usecase.money;


import fintech.domain.account.AccountId;
import fintech.domain.account.AccountService;
import fintech.usecase.common.UnitOfWorkFactory;

import javax.inject.Inject;

public class MoneyUseCaseImpl implements MoneyUseCase {

    private final UnitOfWorkFactory unitOfWorkFactory;
    private final AccountService accountService;

    @Inject
    public MoneyUseCaseImpl(UnitOfWorkFactory unitOfWorkFactory, AccountService accountService) {
        this.unitOfWorkFactory = unitOfWorkFactory;
        this.accountService = accountService;
    }

    @Override
    public long getState(AccountId accountId) {
        return unitOfWorkFactory.call(uow -> accountService.getState(accountId, uow));
    }

    @Override
    public long deposit(AccountId accountId, long moneyAmount) {
        return unitOfWorkFactory.call(uow -> accountService.deposit(accountId, moneyAmount, uow));
    }

    @Override
    public long transfer(AccountId senderId, AccountId receiverId, long moneyAmount) {
        return unitOfWorkFactory.call(uow -> accountService.transfer(senderId, receiverId, moneyAmount, uow));
    }

}
