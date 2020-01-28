package fintech.api.msg;

public class TransferRequest {

    public final String senderId;
    public final String receiverId;
    public final long amount;

    @SuppressWarnings("unused")
    // for serializer
    private TransferRequest() {
        this("", "", 0L);
    }

    public TransferRequest(String senderId, String receiverId, long amount) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }
}
