package com.springbank.user.cmd.api.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class RemoveUserCommand {

    @TargetAggregateIdentifier
    private String id;

    public RemoveUserCommand(String id) {
        this.id = id;
    }
}
