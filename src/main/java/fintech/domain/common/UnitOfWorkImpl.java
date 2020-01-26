package fintech.domain.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class UnitOfWorkImpl implements UnitOfWork {

    private final Map<Object, ReentrantLock> globalLocks;
    private final List<ReentrantLock> acquiredLocks;
    private final List<Runnable> flushCallbacks;

    public UnitOfWorkImpl(Map<Object, ReentrantLock> globalLocks) {
        this.globalLocks = globalLocks;
        flushCallbacks = new ArrayList<>();
        acquiredLocks = new ArrayList<>();
    }

    @Override
    public void takeLock(Object accountId) {
        ReentrantLock lock = globalLocks.computeIfAbsent(accountId, it -> new ReentrantLock());
        lock.lock();
        acquiredLocks.add(lock);
    }

    @Override
    public void register(Runnable flushCallback) {
        flushCallbacks.add(flushCallback);
    }

    @Override
    public void flush() {
        flushCallbacks.forEach(Runnable::run);
        acquiredLocks.forEach(ReentrantLock::unlock);
    }
}
