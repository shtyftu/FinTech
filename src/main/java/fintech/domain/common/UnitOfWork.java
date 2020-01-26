package fintech.domain.common;

public interface UnitOfWork {

    void takeLock(Object accountId);

    void register(Runnable flushCallback);

    void flush();

}
