package fintech.data;


import fintech.domain.account.Account;
import fintech.domain.account.AccountId;
import fintech.domain.account.AccountRepository;
import fintech.domain.common.UnitOfWork;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {

    private final Map<AccountId, AccountImpl> accounts;

    public AccountRepositoryImpl() {
        accounts = new HashMap<>();
    }

    @Override
    public Account load(AccountId accountId, UnitOfWork uow) {
        uow.takeLock(accountId);
        AccountImpl account = load(accountId);
        uow.register(() -> accounts.put(accountId, account));
        return account;
    }

    @Override
    public List<Account> load(List<AccountId> accountList, UnitOfWork uow) {
        return accountList.stream().sorted().map(it -> load(it, uow)).collect(Collectors.toList());
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
