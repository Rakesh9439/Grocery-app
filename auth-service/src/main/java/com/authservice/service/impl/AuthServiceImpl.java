package com.authservice.service.impl;

import com.authservice.constant.Role;
import com.authservice.dto.APIResponse;
import com.authservice.dto.LoginDto;
import com.authservice.dto.UserDto;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;
import com.authservice.service.AuthService;
import com.authservice.service.JWTService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private JWTService jwtService;

    private AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public APIResponse<String> register(UserDto userDto) {


        // Check Email Exist or not

        if(userRepository.existsByEmail(userDto.getEmail())) {
            APIResponse<String> response = new APIResponse<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with Email Id exists");
            return response;
        }


        // Encode the password before saving that to the database

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setPassword(encryptedPassword);
        //  user.setRole("ROLE_USER");
        //  user.setRole("ROLE_ADMIN");
        user.setRole(Role.ROLE_USER);
        User savedUser = userRepository.save(user);
//        if (savedUser==null) {
//


        // Handle if saving fails
        if (savedUser == null) {
            throw new RuntimeException("User registration failed"); // Or use a custom exception
        }

        // finaliy save the user and return response as APIResponse


        APIResponse<String> response = new APIResponse<>();
        response.setMessage("Registration Done");
        response.setStatus(201);
        response.setData("User is registered");

        return response;




    }

    @Override
    public APIResponse<String> login(LoginDto loginDto) {

        APIResponse<String> response  = new APIResponse<>();

        UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        );


        try{

            Authentication authenticate = authenticationManager.authenticate(token);
            if(authenticate.isAuthenticated()){

                String jwtToken  = jwtService.generateToken(loginDto.getEmail(),
                        authenticate.getAuthorities().iterator().next().getAuthority());
                response.setMessage("User Login successfull");
                response.setStatus(200);
                // response.setData("User has logged in");
                response.setData(jwtToken);
                return response;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        response.setMessage("Failed");
        response.setStatus(401);
        response.setData("Un-Authorized Access");

        return response;

    }
}
