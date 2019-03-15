package com.mazon.mongo.services;

import com.mazon.mongo.domain.User;
import com.mazon.mongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository; // mecanismo de injeção automática do spring

    public List<User> findAll(){
        List<User> lista = repository.findAll();
        return lista;
    }
}
