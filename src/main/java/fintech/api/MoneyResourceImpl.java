package fintech.api;

import fintech.usecase.money.MoneyUseCase;
import fintech.domain.account.AccountId;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("money")
public class MoneyResourceImpl {

    private final MoneyUseCase useCase;

    @Inject
    public MoneyResourceImpl(MoneyUseCase useCase) {
        this.useCase = useCase;
    }

    @GET
    @Path("state")
    public long getState(@QueryParam("id") String id) {
        return useCase.getState(new AccountId(id));
    }

    @POST
    @Path("deposit")
    public void deposit(@QueryParam("id") String id, @QueryParam("amount") long amount) {
        useCase.deposit(new AccountId(id), amount);
    }

    @POST
    @Path("transfer")
    public void transfer(
            @QueryParam("senderId") String senderId,
            @QueryParam("receiverId") String receiverId,
            @QueryParam("amount") long amount
    ) {
        useCase.transfer(new AccountId(senderId), new AccountId(receiverId), amount);
    }

}
