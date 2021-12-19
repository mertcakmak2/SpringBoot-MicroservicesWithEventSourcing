package com.springbank.bankacc.api.handler;

import com.springbank.bankacc.api.event.AccountClosedEvent;
import com.springbank.bankacc.api.event.AccountOpenedEvent;
import com.springbank.bankacc.api.event.FundsDepositedEvent;
import com.springbank.bankacc.api.event.FundsWithdrawnEvent;

public interface AccountEventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
