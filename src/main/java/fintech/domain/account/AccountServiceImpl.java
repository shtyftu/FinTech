package fintech.domain.account;

import fintech.domain.common.UnitOfWork;

import javax.inject.Inject;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Inject
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public long getState(AccountId accountId, UnitOfWork uow) {
        return repository.loadReadOnly(accountId).getMoney();
    }

    @Override
    public void deposit(AccountId accountId, long moneyAmount, UnitOfWork uow) {
        repository.load(accountId, uow).depositForced(moneyAmount);
    }

    @Override
    public void transfer(AccountId senderId, AccountId receiverId, long moneyAmount, UnitOfWork uow) {
        List<Account> accounts = repository.load(List.of(senderId, receiverId), uow);
        accounts.get(0).withdraw(moneyAmount);
        accounts.get(1).depositForced(moneyAmount);
    }
}
