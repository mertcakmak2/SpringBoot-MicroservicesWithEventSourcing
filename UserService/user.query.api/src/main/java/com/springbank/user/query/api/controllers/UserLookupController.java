package com.springbank.user.query.api.controllers;

import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.queries.FindAllUsersQuery;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/userLookup")
public class UserLookupController {

    private final QueryGateway queryGateway;

    @Autowired
    public UserLookupController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/")
    public ResponseEntity<UserLookupResponse> getAllUsers(){
        try {
            var query = new FindAllUsersQuery();
            var response =
                    queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            var safeErrorMessage = "Failed to complete get all users request";
            System.out.println(e.toString());
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable String id){
        try {
            var query = new FindUserByIdQuery(id);
            var response =
                    queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            var safeErrorMessage = "Failed to complete get user by id request";
            System.out.println(e.toString());
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
