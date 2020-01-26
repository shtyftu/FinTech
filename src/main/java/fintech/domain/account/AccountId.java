package fintech.domain.account;

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
}
