package com.springbank.bankacc.api.event;

import com.springbank.bankacc.api.model.AccountType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AccountOpenedEvent {

    private String id;
    private String acoountHolderId;
    private AccountType accountType;
    private Date creationDate;
    private double openingBalance;
}
