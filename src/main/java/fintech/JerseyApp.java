package fintech;

import fintech.usecase.money.MoneyUseCase;
import fintech.usecase.money.MoneyUseCaseImpl;
import fintech.usecase.common.UnitOfWorkFactory;
import fintech.infra.UnitOfWorkFactoryImpl;
import fintech.domain.account.AccountRepository;
import fintech.infra.AccountRepositoryImpl;
import fintech.domain.account.AccountService;
import fintech.domain.account.AccountServiceImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;
import javax.ws.rs.ext.Provider;

@Provider
class JerseyApp extends ResourceConfig {
    public JerseyApp() {
        register(new Binder());
        packages("fintech.api");
    }

    static class Binder extends AbstractBinder {

        @Override
        protected void configure() {
            bind(MoneyUseCaseImpl.class).to(MoneyUseCase.class).in(Singleton.class);
            bind(UnitOfWorkFactoryImpl.class).to(UnitOfWorkFactory.class).in(Singleton.class);
            bind(AccountServiceImpl.class).to(AccountService.class).in(Singleton.class);
            bind(AccountRepositoryImpl.class).to(AccountRepository.class).in(Singleton.class);
        }
    }
}
