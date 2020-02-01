package fintech.infra;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import fintech.domain.account.AccountId;
import fintech.domain.common.UnitOfWork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(DataProviderRunner.class)
public class AccountRepositoryImplTest {

    @Mock
    private UnitOfWork uow;
    @Captor
    private ArgumentCaptor<Object> captor;

    @Test
    @UseDataProvider("dataProviderMethod")
    public void test(List<AccountId> requestedIds, List<AccountId> expectedLocksSequence) {
        //Given
        initMocks(this);
        AccountRepositoryImpl repository = new AccountRepositoryImpl();

        // When
        repository.load(requestedIds, uow);

        // Then
        if (expectedLocksSequence.isEmpty()) {
            verify(uow, never()).takeLock(captor.capture());
            return;
        }
        verify(uow, atLeastOnce()).takeLock(captor.capture());
        List<Object> lockObjects = captor.getAllValues();
        assertThat(lockObjects, is(expectedLocksSequence));

    }

    @DataProvider
    public static List<List<Object>> dataProviderMethod() {
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
