package com.crud.CRUD.java.controller;

import com.crud.CRUD.java.model.User;
import com.crud.CRUD.java.service.UserService;
import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    private Hasher hasher;

    @PostMapping("/nuevo")
    public User nuevoUsuario(@RequestBody User user) {
        String password = user.getHashCode();
        String hashedPassword = toHash(password);
        user.setHashCode(hashedPassword);
        return this.userService.nuevoUsuario(user);
    }

    public String toHash(String text){
        hasher = Hashing.sha256().newHasher();
        hasher.putString(text, Charsets.UTF_8);
        String hashedPassword = hasher.hash().toString();
        return hashedPassword;
    }

    @GetMapping("/todos")
    public Iterable<User> obtenerTodosUsuarios() {
        return this.userService.obtenerTodosUsuarios();
    }

    @PostMapping("/actualizar")
    public User actualizarUsuario(@RequestBody User user) {
        return this.userService.actualizarUsuario(user);
    }

    @GetMapping(value = "/{numDocumento}")
    public boolean eliminarUsuario(@PathVariable(value = "numDocumento") String numDocumento) {
        return this.userService.eliminarUsuario(numDocumento);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        User user = this.userService.searchUser(username);
        String hashPassword = toHash(pwd);
        // Lógica para autenticar al usuario y generar un token JWT
        if (user.getHashCode().equals(hashPassword)){
            String token = getJWTToken(username);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        JwtBuilder builder = Jwts
                .builder();
        builder.setId("softtekJWT");
        builder.setSubject(username);
        builder.claim("authorities",
                grantedAuthorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
        builder.setIssuedAt(new Date(System.currentTimeMillis()));
        builder.setExpiration(new Date(System.currentTimeMillis() + 600000));
        builder.signWith(SignatureAlgorithm.HS512,
                secretKey.getBytes());
        builder.compact();

        String token = builder.compact();

        return "Bearer " + token;
    }
}
