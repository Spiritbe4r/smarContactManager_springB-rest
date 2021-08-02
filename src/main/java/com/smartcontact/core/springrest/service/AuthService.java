package com.smartcontact.core.springrest.service;

import com.smartcontact.core.springrest.dto.LoginRequest;
import com.smartcontact.core.springrest.dto.RegisterRequest;
import com.smartcontact.core.springrest.entities.ConfirmationToken;
import com.smartcontact.core.springrest.entities.Contact;
import com.smartcontact.core.springrest.entities.Role;
import com.smartcontact.core.springrest.entities.User;
import com.smartcontact.core.springrest.repository.ConfirmationTokenRepository;
import com.smartcontact.core.springrest.repository.RoleRepository;
import com.smartcontact.core.springrest.repository.UserRepository;
import com.smartcontact.core.springrest.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleRepository role_repo;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    public void signup(RegisterRequest registerRequest){
        User user =new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        Role roleuser=role_repo.findByName("ROLE_USER");
        user.addRole(roleuser);
        //user.setRoles("ROLE_USER");
        user.setEnabled(false);
        user.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png");
        user.setAbout(registerRequest.getAbout());

        userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("flywithmee20@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailService.sendEmail(mailMessage);

    }
    /*
    public void addContact(Contact contacto,Principal principal){
        String nombre = principal.getName();
        User usuario = this.userRepository.getUsuarioPorEmail(nombre);

        //Asignar a Id Usuario
        contacto.setUser(usuario);

        //Agregar el contacto
        usuario.getContactos().add(contacto);

        //Guardar
        this.userRepository.save(usuario);
    }*/

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}
