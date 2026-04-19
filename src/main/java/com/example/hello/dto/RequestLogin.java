package com.example.hello.dto;

import lombok.Data;

//POJO: Plain Old Java Object
@Data
public class RequestLogin {
    private String id;
    private String password;
}
