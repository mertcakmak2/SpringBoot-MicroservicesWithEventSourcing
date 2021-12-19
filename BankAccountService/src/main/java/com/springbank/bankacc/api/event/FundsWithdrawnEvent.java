package com.springbank.bankacc.api.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FundsWithdrawnEvent {

    private String id;
    private double amount;
    private double balance;
}
