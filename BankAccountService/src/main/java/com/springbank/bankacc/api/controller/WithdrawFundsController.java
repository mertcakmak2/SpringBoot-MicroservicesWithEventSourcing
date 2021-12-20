package com.springbank.bankacc.api.controller;

import com.springbank.bankacc.api.command.WithdrawFundsCommand;
import com.springbank.bankacc.api.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {

    private final CommandGateway commandGateway;

    @Autowired
    public WithdrawFundsController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable String id, @RequestBody WithdrawFundsCommand command){
        try {
            command.setId(id);
            commandGateway.send(command).get();
            return new ResponseEntity<>(new BaseResponse("Withdrawal successfully completed"), HttpStatus.OK);
        } catch (Exception e){
            var safeErrorMessage = "Error while processing for id - "+ id;
            System.out.println(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
