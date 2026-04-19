package com.example.hello.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //모든 private 데이터 멤버를
public class ResponseUser {
    private String id;
    private String name;
    private String email;
}
