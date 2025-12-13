package com.upregotdev.subscription_manager.service;

import com.upregotdev.subscription_manager.dto.RegisterRequest;
import com.upregotdev.subscription_manager.entities.User;

public interface UserService {
    User registerUser(RegisterRequest request);
}