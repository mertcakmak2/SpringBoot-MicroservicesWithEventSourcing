package com.springbank.user.query.api.dto;

import com.springbank.user.core.dto.BaseResponse;
import com.springbank.user.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class UserLookupResponse extends BaseResponse {

    private List<User> user;

    public UserLookupResponse(List<User> user) {
        super(null);
        this.user = user;
    }

    public UserLookupResponse(String message) {
        super(message);
    }

    public UserLookupResponse(String message, List<User> user) {
        super(message);
        this.user = user;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
