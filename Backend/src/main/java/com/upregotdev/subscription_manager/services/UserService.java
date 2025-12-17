package com.upregotdev.subscription_manager.services;

import com.upregotdev.subscription_manager.dto.RegisterRequest;
import com.upregotdev.subscription_manager.entities.User;

public interface UserService {
    User registerUser(RegisterRequest request);
}