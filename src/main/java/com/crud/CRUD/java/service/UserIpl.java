package com.crud.CRUD.java.service;

import com.crud.CRUD.java.connection.UserConnect;
import com.crud.CRUD.java.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserIpl implements UserService {

    @Autowired
    private UserConnect userConnect;

    @Override
    public User nuevoUsuario(User user) {
        return userConnect.save(user);
    }

    @Override
    public Iterable<User> obtenerTodosUsuarios() {
        return this.userConnect.findAll();
    }

    @Override
    public User actualizarUsuario(User user) {
        Optional<User> userOptional = this.userConnect.findById(user.getNumDocument());
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setSecondName(user.getSecondName());
            existingUser.setNit(user.getNit());
            existingUser.setRol(user.getRol());
            existingUser.setEmail(user.getEmail());
            existingUser.setHashCode(user.getHashCode());
            existingUser.setGender(user.getGender());
            
            return this.nuevoUsuario(existingUser);
        }
        return null;
    }

    @Override
    public User searchUser(String numDocument){
        Optional<User> userOptional = this.userConnect.findById(numDocument);
        User user = userOptional.get();
        return user;
    }

    @Override
    public boolean eliminarUsuario(String numDocument) {
        this.userConnect.deleteById(numDocument);
        return true;
    }
}
