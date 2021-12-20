package com.springbank.bankacc.api.controller;

import com.springbank.bankacc.api.command.DepositFundsCommand;
import com.springbank.bankacc.api.dto.BaseResponse;
import com.springbank.bankacc.api.dto.OpenAccountResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundsController {

    private final CommandGateway commandGateway;

    @Autowired
    public DepositFundsController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable String id, @RequestBody DepositFundsCommand command){
        try {
            command.setId(id);
            commandGateway.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds successfully deposited"), HttpStatus.OK);
        } catch (Exception e){
            var safeErrorMessage = "Error while processing for id - "+ id;
            System.out.println(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
