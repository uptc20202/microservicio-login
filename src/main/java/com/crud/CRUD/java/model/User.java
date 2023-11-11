package com.crud.CRUD.java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class User {

	@Id
    @Column
    private String numDocument;
	@Column
    private String firstName;
	@Column
    private String secondName;
	@Column
    private BigDecimal nit;
	@Column
    private String rol;
	@Column
    private String email;
	@Column
    private String hashCode;
	@Column
    private char gender;
}
