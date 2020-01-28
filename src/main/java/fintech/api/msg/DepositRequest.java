package fintech.api.msg;

public class DepositRequest {

    public final String accountId;
    public final long moneyAmount;

    @SuppressWarnings("unused")
    // for serializer
    private DepositRequest() {
        this("", 0);
    }

    public DepositRequest(String accountId, long moneyAmount) {
        this.accountId = accountId;
        this.moneyAmount = moneyAmount;
    }
}
