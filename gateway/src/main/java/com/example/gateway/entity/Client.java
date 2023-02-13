package com.example.gateway.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
@Entity
@Getter
@Setter
public class Client extends User{
    public Client() {
        setRole(RoleEnum.CLIENT);
    }
}
