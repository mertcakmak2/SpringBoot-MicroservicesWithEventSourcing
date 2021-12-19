package com.springbank.bankacc.api.handler;

import com.springbank.bankacc.api.event.AccountClosedEvent;
import com.springbank.bankacc.api.event.AccountOpenedEvent;
import com.springbank.bankacc.api.event.FundsDepositedEvent;
import com.springbank.bankacc.api.event.FundsWithdrawnEvent;
import com.springbank.bankacc.api.model.BankAccount;
import com.springbank.bankacc.api.repository.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("bankaccount-group")
public class AccountEventHandlerImpl implements AccountEventHandler {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountEventHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @EventHandler
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .acoountHolderId(event.getAcoountHolderId())
                .creationDate(event.getCreationDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance()).build();

        accountRepository.save(bankAccount);
    }

    @Override
    @EventHandler
    public void on(FundsDepositedEvent event) {
        var bankAcoount = accountRepository.findById(event.getId());
        if(bankAcoount.isEmpty()) return;

        bankAcoount.get().setBalance(event.getBalance());
        accountRepository.save(bankAcoount.get());
    }

    @Override
    @EventHandler
    public void on(FundsWithdrawnEvent event) {
        var bankAcoount = accountRepository.findById(event.getId());
        if(bankAcoount.isEmpty()) return;

        bankAcoount.get().setBalance(event.getBalance());
        accountRepository.save(bankAcoount.get());
    }

    @Override
    @EventHandler
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
