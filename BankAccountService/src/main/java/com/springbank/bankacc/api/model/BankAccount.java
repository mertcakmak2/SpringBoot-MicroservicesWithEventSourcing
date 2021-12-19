package com.springbank.bankacc.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {

    @Id
    private String id;
    private String acoountHolderId;
    private Date creationDate;
    private AccountType accountType;
    private double balance;

}
