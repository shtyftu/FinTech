package fintech.infra.common;

import fintech.domain.common.UnitOfWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

class UnitOfWorkImpl implements UnitOfWork {

    private final Map<Object, ReentrantLock> globalLocks;
    private final List<ReentrantLock> acquiredLocks;
    private final List<Runnable> flushCallbacks;

    private boolean closed;
    private boolean flushed;

    UnitOfWorkImpl(Map<Object, ReentrantLock> globalLocks) {
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
        if (flushed) {
            throw new DoubleFlushedUnitException();
        }
        if (closed) {
            throw new FlushClosedUnitException();
        }
        flushCallbacks.forEach(Runnable::run);
        flushed = true;
    }

    @Override
    public void takeLocks(List<? extends Comparable<?>> lockObjects) {
        lockObjects.stream().sorted().forEach(this::takeLock);
    }

    @Override
    public void close() {
        if (closed) {
            throw new DoubleClosedUniteException();
        }
        acquiredLocks.forEach(ReentrantLock::unlock);
        closed = true;
    }

    private static class DoubleFlushedUnitException extends RuntimeException {
    }

    private static class DoubleClosedUniteException extends RuntimeException {
    }

    private static class FlushClosedUnitException extends RuntimeException {
    }
}
