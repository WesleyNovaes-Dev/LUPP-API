package com.example.LUPP_API.controller;

import com.example.LUPP_API.domain.user.*;
import com.example.LUPP_API.infra.security.TokenService;
import com.example.LUPP_API.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;



    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role(), data.name(), data.points());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all-users")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(this.repository.findAll());
    }

    // Método para recuperar o ID do usuário logado
    private UUID getLoggedUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;
        return user.getId();  // Retorna o user_id do usuário logado
    }

    @GetMapping("/my-profile")
    public ResponseEntity<?> getMyProfile() {
        UUID userId = getLoggedUserId();

        return repository.findById(userId)
                .map(user -> ResponseEntity.ok(UserDTO.from(user)))
                .orElse(ResponseEntity.notFound().build());
    }



}
