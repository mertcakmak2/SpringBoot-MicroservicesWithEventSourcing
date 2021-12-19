package com.springbank.bankacc.api.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CloseAcoountCommand {

    @TargetAggregateIdentifier
    private String id;

}
