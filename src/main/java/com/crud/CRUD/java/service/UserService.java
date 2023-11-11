package com.crud.CRUD.java.service;

import com.crud.CRUD.java.model.User;

public interface UserService {

    User nuevoUsuario(User user);
    Iterable<User> obtenerTodosUsuarios();
    User actualizarUsuario(User user);
    boolean eliminarUsuario(String numDocument);
    User searchUser(String numDocument);

}
