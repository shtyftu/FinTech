package fintech.domain.account;

import fintech.domain.common.UnitOfWork;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

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
    public long deposit(AccountId accountId, long moneyAmount, UnitOfWork uow) {
        return repository.load(accountId, uow).depositForced(moneyAmount);
    }

    @Override
    public long transfer(AccountId senderId, AccountId receiverId, long moneyAmount, UnitOfWork uow) {
        Map<AccountId, Account> accountMap = repository.load(List.of(senderId, receiverId), uow);

        accountMap.get(senderId).withdraw(moneyAmount);
        accountMap.get(receiverId).depositForced(moneyAmount);

        return accountMap.get(senderId).getMoney();
    }
}
