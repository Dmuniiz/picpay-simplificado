package com.picpaysimplificado.controller;

import com.picpaysimplificado.DTO.LoginDTO;
import com.picpaysimplificado.DTO.TokenJwtDTO;
import com.picpaysimplificado.DTO.UserDTO;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.infra.security.TokenService;
import com.picpaysimplificado.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/create-user")
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
        User newUser = userService.createUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity startLogin(@RequestBody @Valid LoginDTO data){

        var userAuthenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());//dto spring security

        var authenticationManager = manager.authenticate(userAuthenticationToken);//classe que despara o processo de autenticacao -> chama a service e checa se o usuário existe atraves da classe de entidade

        String tokenJWT = tokenService.generateJWT((User) authenticationManager.getPrincipal());

        return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();//204
    }


}
