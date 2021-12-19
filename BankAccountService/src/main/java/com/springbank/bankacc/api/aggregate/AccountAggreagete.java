package com.springbank.bankacc.api.aggregate;

import com.springbank.bankacc.api.command.CloseAcoountCommand;
import com.springbank.bankacc.api.command.DepositFundsCommand;
import com.springbank.bankacc.api.command.OpenAcoountCommand;
import com.springbank.bankacc.api.command.WithdrawFundsCommand;
import com.springbank.bankacc.api.event.AccountClosedEvent;
import com.springbank.bankacc.api.event.AccountOpenedEvent;
import com.springbank.bankacc.api.event.FundsDepositedEvent;
import com.springbank.bankacc.api.event.FundsWithdrawnEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class AccountAggreagete {
    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private double balance;

    @CommandHandler
    private AccountAggreagete(OpenAcoountCommand command){
        var event = AccountOpenedEvent.builder()
                .id(command.getId())
                .acoountHolderId(command.getAccountHolderId())
                .accountType(command.getAccountType())
                .creationDate(new Date())
                .openingBalance(command.getOpeningBalance()).build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountOpenedEvent event){
        this.id = event.getId();
        this.accountHolderId = event.getAcoountHolderId();
        this.balance = event.getOpeningBalance();
    }

    @CommandHandler
    public void handle(DepositFundsCommand command){
        var amaount = command.getAmount();
        var event = FundsDepositedEvent.builder()
                .id(command.getId())
                .amount(amaount)
                .balance(this.balance + amaount).build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(FundsDepositedEvent event){
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(WithdrawFundsCommand command){
        var amount = command.getAmount();

        if(this.balance - amount < 0){
            throw new IllegalStateException("withdrawal declined, insufficient funds");
        }

        var event = FundsWithdrawnEvent.builder()
                .id(command.getId())
                .amount(amount)
                .balance(this.balance - amount).build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(FundsWithdrawnEvent event){
        this.balance -= event.getAmount();
    }

    @CommandHandler
    public void handle(CloseAcoountCommand command){
        var event = AccountClosedEvent.builder()
                .id(command.getId()).build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountClosedEvent event){
        AggregateLifecycle.markDeleted();
    }
}
