package fintech.infra.account;


import fintech.domain.account.Account;
import fintech.domain.account.AccountId;
import fintech.domain.account.AccountImpl;
import fintech.domain.account.AccountRepository;
import fintech.domain.common.UnitOfWork;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {

    private final Map<AccountId, AccountImpl> accounts;

    public AccountRepositoryImpl() {
        accounts = new ConcurrentHashMap<>();
    }

    @Override
    public Account load(AccountId accountId, UnitOfWork uow) {
        uow.takeLock(accountId);
        return loadAndRegister(accountId, uow);
    }

    @Override
    public Map<AccountId, Account> load(List<AccountId> accountIds, UnitOfWork uow) {
        uow.takeLocks(accountIds);
        return accountIds.stream().collect(Collectors.toMap(it -> it, id -> loadAndRegister(id, uow)));
    }

    @Override
    public Account loadReadOnly(AccountId accountId) {
        return loadInternal(accountId);
    }

    private Account loadAndRegister(AccountId accountId, UnitOfWork uow) {
        AccountImpl account = loadInternal(accountId);
        uow.register(() -> accounts.put(accountId, account));
        return account;
    }

    private AccountImpl loadInternal(AccountId accountId) {
        return accounts.computeIfAbsent(accountId, it -> new AccountImpl(0)).copy();
    }

}
