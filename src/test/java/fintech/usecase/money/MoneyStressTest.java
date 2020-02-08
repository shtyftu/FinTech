package fintech.usecase.money;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MoneyStressTest extends AMoneyTest {

    @Test(timeout = 15000L)
    public void stress() throws InterruptedException {

        int accountCount = 3;
        long startMoney = 100000L;
        IntStream.range(0, accountCount).forEach(it -> deposit(accountId(it), startMoney));

        int iterationCount = 1000;
        int threadCount = 15;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        long transferMoney = 1L;

        List<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < iterationCount; i++) {
            for (int accountIndex = 0; accountIndex < accountCount; accountIndex++) {
                final int index = accountIndex;
                tasks.add(() -> transfer(accountId(index), accountId((index + 1) % accountCount), transferMoney));
            }
        }

        executorService.invokeAll(tasks);

        IntStream.range(0, accountCount).forEach(it -> assertThat(state(accountId(it)).currentMoney, is(startMoney)));
    }

    private static String accountId(int index) {
        return String.valueOf(index);
    }
}
