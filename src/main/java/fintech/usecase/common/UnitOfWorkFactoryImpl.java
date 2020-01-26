package fintech.usecase.common;


import fintech.domain.common.UnitOfWorkImpl;
import fintech.domain.common.UnitOfWork;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class UnitOfWorkFactoryImpl implements UnitOfWorkFactory {

    private final Map<Object, ReentrantLock> globalLocks;

    public UnitOfWorkFactoryImpl() {
        globalLocks = new ConcurrentHashMap<>();
    }

    @Override
    public UnitOfWork create() {
        return new UnitOfWorkImpl(globalLocks);
    }
}
