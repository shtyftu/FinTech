package fintech.infra.common;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import fintech.domain.account.AccountId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(DataProviderRunner.class)
public class UnitOfWorkImplTest {

    @Mock
    private Map<Object, ReentrantLock> lockMap;
    @Mock
    private ReentrantLock lock;
    @Captor
    private ArgumentCaptor<Object> captor;


    @Test
    @UseDataProvider("takeLocksDP")
    public <T extends Comparable<T>> void takeLocks(List<T> requestedIds, List<T> expectedLocksSequence) {
        //Given
        initMocks(this);
        when(lockMap.computeIfAbsent(any(), any())).thenReturn(lock);
        UnitOfWorkImpl uow = new UnitOfWorkImpl(lockMap);

        // When
        uow.takeLocks(requestedIds);

        // Then
        if (expectedLocksSequence.isEmpty()) {
            verify(lockMap, never()).computeIfAbsent(any(), any());
            return;
        }
        verify(lockMap, atLeastOnce()).computeIfAbsent(captor.capture(), any());
        List<Object> lockObjects = captor.getAllValues();
        assertThat(lockObjects, is(expectedLocksSequence));
    }

    @DataProvider
    public static List<List<Object>> takeLocksDP() {
        return List.of(
                List.of(
                        idsFrom(List.of("1", "2", "3")),
                        idsFrom(List.of("3", "2", "1"))
                ),
                List.of(
                        idsFrom(List.of("3", "2", "1")),
                        idsFrom(List.of("3", "2", "1"))
                ),
                List.of(
                        idsFrom(List.of("5", "2", "9", "3", "1", "7", "4")),
                        idsFrom(List.of("9", "7", "5", "4", "3", "2", "1"))
                ),
                List.of(
                        List.of(),
                        List.of()
                )
        );
    }

    private static List<AccountId> idsFrom(List<String> strings) {
        return strings.stream().map(AccountId::new).collect(Collectors.toList());
    }
}
