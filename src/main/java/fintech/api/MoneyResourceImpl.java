package fintech.api;

import fintech.api.msg.*;
import fintech.domain.account.AccountId;
import fintech.usecase.money.MoneyUseCase;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("money")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MoneyResourceImpl {

    private final MoneyUseCase useCase;

    @Inject
    public MoneyResourceImpl(MoneyUseCase useCase) {
        this.useCase = useCase;
    }

    @POST
    @Path("state")
    public GetStateResponse getState(GetStateRequest request) {
        return new GetStateResponse(useCase.getState(new AccountId(request.accountId)));
    }

    @POST
    @Path("deposit")
    public DepositResponse deposit(DepositRequest request) {
        return new DepositResponse(useCase.deposit(new AccountId(request.accountId), request.moneyAmount));
    }

    @POST
    @Path("transfer")
    public TransferResponse transfer(TransferRequest request) {
        return new TransferResponse(
                useCase.transfer(new AccountId(request.senderId),new AccountId(request.receiverId),request.amount)
        );
    }

}
