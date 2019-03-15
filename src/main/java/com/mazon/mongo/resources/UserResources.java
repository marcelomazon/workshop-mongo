package com.mazon.mongo.resources;

import com.mazon.mongo.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping (value = "/users")
public class UserResources {

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        User maria = new User("1","Maria", "maria@teste");
        User jose = new User("2","Jose", "jose@teste");

        List<User> list = new ArrayList<>();
        list.addAll(Arrays.asList(maria, jose));

        return ResponseEntity.ok().body(list);
    }



}
