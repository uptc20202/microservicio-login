package com.crud.CRUD.java.connection;

import com.crud.CRUD.java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserConnect extends JpaRepository<User, String> {

}
