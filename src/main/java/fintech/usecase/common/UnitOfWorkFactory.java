package fintech.usecase.common;


import fintech.domain.common.UnitOfWork;

import java.util.function.Function;

public interface UnitOfWorkFactory {

    UnitOfWork create();

    <T> T call(Function<UnitOfWork, T> uowConsumer);
}
