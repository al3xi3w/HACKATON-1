package org.example.init.domain;


import org.example.init.DTO.JwtAuthenticationResponse;
import org.example.init.DTO.SigninRequest;
import org.example.init.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    UserAccountRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public JwtAuthenticationResponse signup(UserAccount user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserAccount savedUser = userRepository.save(user);
        //applicationEventPublisher.publishEvent(new WelcomeEmailEvent(this, savedUser.getEmail(), savedUser.getFirstName()));
        var jwt = jwtService.generateToken(user);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);

        return response;
    }

    public JwtAuthenticationResponse signin(SigninRequest request) throws IllegalArgumentException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail());
        var jwt = jwtService.generateToken(user);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);

        return response;
    }
}
