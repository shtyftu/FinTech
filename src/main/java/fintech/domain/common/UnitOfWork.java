package fintech.domain.common;

import java.util.List;

public interface UnitOfWork extends AutoCloseable {

    void takeLocks(List<? extends Comparable<?>> lockObjects);

    void takeLock(Object accountId);

    void register(Runnable flushCallback);

    void flush();

}
