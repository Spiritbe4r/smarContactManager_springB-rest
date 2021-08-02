package com.smartcontact.core.springrest.controller;

import com.smartcontact.core.springrest.dto.LoginRequest;
import com.smartcontact.core.springrest.dto.RegisterRequest;
import com.smartcontact.core.springrest.entities.ConfirmationToken;
import com.smartcontact.core.springrest.entities.User;
import com.smartcontact.core.springrest.repository.ConfirmationTokenRepository;
import com.smartcontact.core.springrest.repository.UserRepository;
import com.smartcontact.core.springrest.service.AuthService;
import com.smartcontact.core.springrest.service.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody RegisterRequest registerRequest){
        Map<String, Object> mapRespuesta = new HashMap<>();
        try{
            authService.signup(registerRequest);

            mapRespuesta.put("rpta", "1");
            mapRespuesta.put("msg", "Nuevo Usuario creado correctamente!!");
            mapRespuesta.put("data", registerRequest);

        }catch (Exception e){
            mapRespuesta.put("rpta", "-1");
            mapRespuesta.put("msg", e.getMessage());

        }

        return new ResponseEntity<>(mapRespuesta, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        User user=userRepository.getUsuarioPorEmail(loginRequest.getEmail());




        return authService.login(loginRequest);
    }


    @PostMapping("/confirm-account")
    public ResponseEntity <Object>confirm( @RequestParam("token")String confirmationToken)
    {
        Map<String, Object> mapRespuesta = new HashMap<>();
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepository.getUsuarioPorEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            mapRespuesta.put("rpta", "1");
            mapRespuesta.put("message","User activated!");
        }
        else
        {
            mapRespuesta.put("msg","The link is invalid or broken!");

        }

        return new ResponseEntity<>(mapRespuesta, HttpStatus.OK);
    }
}
