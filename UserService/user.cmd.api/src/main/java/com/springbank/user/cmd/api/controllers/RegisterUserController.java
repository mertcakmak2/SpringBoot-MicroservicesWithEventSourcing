package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.dto.RegisterUserResponse;
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
@RequestMapping(path = "/api/v1/registerUser")
public class RegisterUserController {

    private final CommandGateway commandGateway;

    @Autowired
    public RegisterUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping()
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserCommand command){
        var id = UUID.randomUUID().toString();
        command.setId(id);

        try {
            var response = commandGateway.sendAndWait(command);
            var registerUserResponse = new RegisterUserResponse(id,"User successfully registered!");
            return new ResponseEntity<>(registerUserResponse, HttpStatus.CREATED);
        } catch (Exception e){
            var safeErrorMessage = "Error while proccessing register user request for id = " + command.getUser();
            System.out.println(e.toString());
            return new ResponseEntity<>(new RegisterUserResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
