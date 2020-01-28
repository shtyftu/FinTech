package fintech.api.msg;

public class DepositResponse {

    public final long currentMoney;

    @SuppressWarnings("unused")
    // for serializer
    private DepositResponse() {
        this(0);
    }

    public DepositResponse(long currentMoney) {
        this.currentMoney = currentMoney;
    }
}
