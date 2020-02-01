package fintech.infra;


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
        AccountImpl account = load(accountId);
        uow.register(() -> accounts.put(accountId, account));
        return account;
    }

    @Override
    public Map<AccountId, Account> load(List<AccountId> accountList, UnitOfWork uow) {
        //sorted() method is used to avoid deadlock
        //noinspection RedundantStreamOptionalCall
        return accountList.stream().sorted().collect(Collectors.toMap(it -> it, it -> load(it, uow)));
    }

    @Override
    public Account loadReadOnly(AccountId accountId) {
        return load(accountId);
    }

    private AccountImpl load(AccountId accountId) {
        return accounts.computeIfAbsent(accountId, it -> new AccountImpl(0)).copy();
    }

    public static class NotEnoughMoneyException extends RuntimeException {
    }
}
