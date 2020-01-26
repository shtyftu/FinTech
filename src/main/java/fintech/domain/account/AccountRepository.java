package fintech.domain.account;


import fintech.domain.common.UnitOfWork;

import java.util.List;

public interface AccountRepository {

    Account load(AccountId accountId, UnitOfWork uow);

    List<Account> load(List<AccountId> accountId, UnitOfWork uow);

    Account loadReadOnly(AccountId accountId);
}
