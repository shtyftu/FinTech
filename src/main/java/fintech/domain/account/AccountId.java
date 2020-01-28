package fintech.domain.account;

import java.util.Objects;

public class AccountId implements Comparable<AccountId> {

    public final String value;

    public AccountId(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(AccountId o) {
        if (o == null || o.value == null) {
            return 1;
        }
        return o.value.compareTo(this.value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId = (AccountId) o;
        return value.equals(accountId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
