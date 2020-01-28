package fintech.api.msg;

public class GetStateRequest {

    public final String accountId;

    @SuppressWarnings("unused")
    // for serializer
    private GetStateRequest() {
        this("");
    }

    public GetStateRequest(String accountId) {
        this.accountId = accountId;
    }
}
