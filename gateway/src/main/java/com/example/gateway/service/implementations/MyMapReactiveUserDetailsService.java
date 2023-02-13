package com.example.gateway.service.implementations;

import com.example.gateway.configuration.security.JwtUtil;
import com.example.gateway.dto.AuthDTO;
import com.example.gateway.entity.Client;
import com.example.gateway.entity.RoleEnum;
import com.example.gateway.entity.User;
import com.example.gateway.exceptions.RegisterException;
import com.example.gateway.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
@Service
public class MyMapReactiveUserDetailsService extends MapReactiveUserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    private ReactiveAuthenticationManager authManager;
    @Autowired
    JwtUtil jwtUtil;
    ObjectMapper objectMapper=new ObjectMapper();
    public MyMapReactiveUserDetailsService(){
        super(new org.springframework.security.core.userdetails.User("admin","passpass",new ArrayList<>()));
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        Optional<User> optionalUser=userRepository.findByEmail(username);
        if(!optionalUser.isPresent())
            throw new UsernameNotFoundException("could not find user with email "+username);

        User user=optionalUser.get();

        return Mono.just(new org.springframework.security.core.userdetails.User(username,
                user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getRole().toString()))));
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).get();
    }
    public AuthDTO register(final AuthDTO userDTO) throws RegisterException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent())
            throw new RegisterException("email already in use");

        if (userDTO.getRole().equals(RoleEnum.CLIENT)) {
            //if a client is registering
            Client c = new Client();
            c.setEmail(userDTO.getEmail());
            c.setFirstName(userDTO.getFirstName());
            c.setLastName(userDTO.getLastName());
            c.setBanned(false);
            c.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            c = userRepository.save(c);

            userDTO.setPassword(null);
            userDTO.setJwtToken(jwtUtil.generateToken(userDTO.getEmail()));
            return userDTO;
        }

        throw new UsernameNotFoundException("invalid user role");
    }
    public AuthDTO login(AuthDTO loginDTO) throws AuthenticationCredentialsNotFoundException{
            UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword());
            final AuthDTO responseDto=new AuthDTO();
            User u=userRepository.findByEmail(loginDTO.getEmail()).get();
            responseDto.setRole(u.getRole());
            String jwtToken=jwtUtil.generateToken(loginDTO.getEmail());
            responseDto.setJwtToken(jwtToken);
            return responseDto;
    }

    }
