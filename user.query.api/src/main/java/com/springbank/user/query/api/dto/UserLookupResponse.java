package com.springbank.user.query.api.dto;

import com.springbank.user.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLookupResponse {
    private User user;
}
