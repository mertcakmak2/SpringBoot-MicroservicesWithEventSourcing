package com.springbank.bankacc.api.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FundsDepositedEvent {

    private String id;
    private double amount;
    private double balance;
}
