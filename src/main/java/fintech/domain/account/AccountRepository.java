package fintech.domain.account;


import fintech.domain.common.UnitOfWork;

import java.util.List;
import java.util.Map;

public interface AccountRepository {

    Account load(AccountId accountId, UnitOfWork uow);

    Map<AccountId, Account> load(List<AccountId> accountId, UnitOfWork uow);

    Account loadReadOnly(AccountId accountId);
}
