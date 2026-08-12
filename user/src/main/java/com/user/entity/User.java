package com.user.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    @Field("name")
    private String name;
    @Indexed(unique = true)
    @Field("email")
    private String email;
    @Field("role")
    private String role;
    @Field("password")
    private String password;
}