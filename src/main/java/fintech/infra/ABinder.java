package fintech.infra;

import fintech.domain.account.AccountRepository;
import fintech.domain.account.AccountService;
import fintech.domain.account.AccountServiceImpl;
import fintech.infra.account.AccountRepositoryImpl;
import fintech.infra.common.UnitOfWorkFactoryImpl;
import fintech.usecase.common.UnitOfWorkFactory;
import fintech.usecase.money.MoneyUseCase;
import fintech.usecase.money.MoneyUseCaseImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

public abstract class ABinder extends AbstractBinder {

        @Override
        protected void configure() {
            bind(MoneyUseCaseImpl.class).to(MoneyUseCase.class).in(Singleton.class);
            bind(UnitOfWorkFactoryImpl.class).to(UnitOfWorkFactory.class).in(Singleton.class);
            bind(AccountServiceImpl.class).to(AccountService.class).in(Singleton.class);
            bind(AccountRepositoryImpl.class).to(AccountRepository.class).in(Singleton.class);
        }
}
