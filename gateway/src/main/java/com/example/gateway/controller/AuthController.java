package com.example.gateway.controller;

import com.example.gateway.dto.AuthDTO;
import com.example.gateway.exceptions.RegisterException;
import com.example.gateway.repository.UserRepository;
import com.example.gateway.service.implementations.MyMapReactiveUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    MyMapReactiveUserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;
    @PostMapping("/register")
    ResponseEntity register(@RequestBody AuthDTO userDTO){
        try{
            AuthDTO authDTO =userDetailsService.register(userDTO);
            return new ResponseEntity<>(authDTO, HttpStatus.CREATED);

        }catch (RegisterException exception){
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    ResponseEntity login(@RequestBody AuthDTO loginDTO) {
        try {
            return new ResponseEntity<>(userDetailsService.login(loginDTO), HttpStatus.ACCEPTED);
        }catch (Exception exception){
            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
        }
    }


}
