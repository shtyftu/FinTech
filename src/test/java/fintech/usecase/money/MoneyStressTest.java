package fintech.usecase.money;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MoneyStressTest extends AMoneyTest {

    @Test
    public void stress() throws InterruptedException {
        int accountCount = 10;
        long startMoney = 1000L;

        IntStream.range(0, accountCount).forEach(it -> deposit(accountId(it), startMoney));

        int iterationCount = 200;
        int threadCount = 15;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        long trasferMoney = 1L;

        List<Callable<Object>> tasks = IntStream.range(0, iterationCount)
                .boxed()
                .map(i -> IntStream.range(0, accountCount)
                        .boxed()
                        .map(index -> (Callable<Object>) () -> transfer(
                                accountId(index),
                                accountId((index + 1) % accountCount),
                                trasferMoney
                        ))
                )
                .reduce(Stream::concat)
                .orElseThrow(RuntimeException::new)
                .collect(Collectors.toList());

        executorService.invokeAll(tasks);

        IntStream.range(0, accountCount).forEach(it -> assertThat(state(accountId(it)).currentMoney, is(startMoney)));

    }

    private static String accountId(int index) {
        return String.valueOf(index);
    }
}
