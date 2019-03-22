package com.mazon.mongo.resources;

import com.mazon.mongo.domain.Post;
import com.mazon.mongo.domain.User;
import com.mazon.mongo.dto.UserDto;
import com.mazon.mongo.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping (value = "/users")
@Api(tags = "Usuários", description = "Usuários do sistema")
public class UserResources {

    @Autowired
    private UserService service;

    @GetMapping
    @ApiOperation("Listar todos os usuários")
    public ResponseEntity<List<UserDto>> findAll(){

        List<User> list = service.findAll();
        List<UserDto> listDto = list.stream()
                .map(x -> new UserDto(x))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter os dados de um usuário pelo Id")
    public ResponseEntity<UserDto> findById(@PathVariable(value = "id") String id){
        User user = service.findById(id);
        return ResponseEntity.ok().body(new UserDto(user));
    }

    @GetMapping("/{id}/posts")
    @ApiOperation("Listar as postagens de um usuário")
    public ResponseEntity<List<Post>> findPosts(@PathVariable(value = "id") String id){
        User user = service.findById(id);
        return ResponseEntity.ok().body(user.getPosts());
    }

    @PostMapping
    @ApiOperation("Inserir um novo usuário")
    public ResponseEntity<Void> insert(@RequestBody UserDto userDto){
        User obj = service.fromDto(userDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Excluir um usuário")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar os dados de um usuário")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto, @PathVariable(value = "id") String id){
        User user = service.fromDto(userDto);
        user.setId(id);
        user = service.update(user);
        return ResponseEntity.ok().body(new UserDto(user));
    }

}