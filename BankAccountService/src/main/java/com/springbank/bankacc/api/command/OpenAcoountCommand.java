package com.springbank.bankacc.api.command;

import com.springbank.bankacc.api.model.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class OpenAcoountCommand {

    @TargetAggregateIdentifier
    private String id;
    private String accountHolderId;
    private AccountType accountType;
    private double openingBalance;
}
