package com.springbank.bankacc.api.controller;

import com.springbank.bankacc.api.command.OpenAcoountCommand;
import com.springbank.bankacc.api.dto.OpenAccountResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {

    private final CommandGateway commandGateway;

    @Autowired
    public OpenAccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping(path = "")
    public ResponseEntity<OpenAccountResponse> openAccount(@RequestBody OpenAcoountCommand command){
        var id = UUID.randomUUID().toString();
        command.setId(id);

        try {
            commandGateway.send(command);
            return new ResponseEntity<>(new OpenAccountResponse(id, "succesfuly opened a new account"), HttpStatus.CREATED);
        }catch (Exception e){
            var safeErrorMessage = "Error while processing for id - "+ id;
            System.out.println(e.toString());
            return new ResponseEntity<>(new OpenAccountResponse(id,safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
