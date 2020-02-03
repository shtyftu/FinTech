package fintech.infra.common;


import fintech.domain.common.UnitOfWork;
import fintech.usecase.common.UnitOfWorkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class UnitOfWorkFactoryImpl implements UnitOfWorkFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<Object, ReentrantLock> globalLocks;

    public UnitOfWorkFactoryImpl() {
        globalLocks = new ConcurrentHashMap<>();
    }

    @Override
    public UnitOfWork create() {
        return new UnitOfWorkImpl(globalLocks);
    }

    @Override
    public <T> T call(Function<UnitOfWork, T> uowConsumer) {
        try (UnitOfWork uow = create()) {
            T result = uowConsumer.apply(uow);
            uow.flush();
            return result;
        } catch (Exception e) {
            logger.error("Work executing error", e);
            throw new RuntimeException(e);
        }
    }
}
