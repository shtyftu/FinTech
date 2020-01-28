package fintech.api.msg;

public class TransferResponse {

    public final long senderMoney;

    @SuppressWarnings("unused")
    // for serializer
    private TransferResponse() {
        this(0L);
    }

    public TransferResponse(long senderMoney) {
        this.senderMoney = senderMoney;
    }
}
