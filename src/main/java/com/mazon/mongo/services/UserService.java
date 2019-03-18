package com.mazon.mongo.services;

import com.mazon.mongo.domain.User;
import com.mazon.mongo.dto.UserDto;
import com.mazon.mongo.services.exception.ObjectNotFoundException;
import com.mazon.mongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository; // mecanismo de injeção automática do spring

    public List<User> findAll() {
        return repository.findAll(Sort.by("name"));
    }

    public User findById(String id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public User insert(User user) {
        return repository.insert(user);
    }

    public User fromDto(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }
}
