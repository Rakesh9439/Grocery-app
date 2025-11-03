package com.authservice.service;

import com.authservice.dto.APIResponse;
import com.authservice.dto.LoginDto;
import com.authservice.dto.UserDto;

public interface AuthService {

    APIResponse<String> register(UserDto userDto);

    APIResponse<String> login(LoginDto loginDto);
}
