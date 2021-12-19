package com.springbank.bankacc.api.repository;

import com.springbank.bankacc.api.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, String> {
}
