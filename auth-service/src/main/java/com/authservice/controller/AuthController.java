package com.authservice.controller;


import com.authservice.dto.APIResponse;
import com.authservice.dto.LoginDto;
import com.authservice.dto.UserDto;
import com.authservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth")
public class AuthController {

  private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    //     http://localhost:8082/api/v1/auth/register
    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto userDto){
        APIResponse<String> response = authService.register(userDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }



    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> login(@RequestBody LoginDto loginDto){
        APIResponse<String> response = authService.login(loginDto);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }


}
