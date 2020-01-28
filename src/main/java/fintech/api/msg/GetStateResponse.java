package fintech.api.msg;

public class GetStateResponse {

    public final long currentMoney;

    @SuppressWarnings("unused")
    // for serializer
    private GetStateResponse() {
        this(0L);
    }

    public GetStateResponse(long currentMoney) {
        this.currentMoney = currentMoney;
    }

}
