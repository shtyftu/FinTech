package fintech.usecase.common;


import fintech.domain.common.UnitOfWork;

import java.util.function.Function;

public interface UnitOfWorkFactory {

    UnitOfWork create();

    default <T> T call(Function<UnitOfWork, T> uowConsumer) {
        UnitOfWork uow = create();
        T result = uowConsumer.apply(uow);
        uow.flush();
        return result;
    }
}
