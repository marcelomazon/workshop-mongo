package com.mazon.mongo.services;

import com.mazon.mongo.domain.Post;
import com.mazon.mongo.domain.User;
import com.mazon.mongo.dto.UserDto;
import com.mazon.mongo.repository.PostRepository;
import com.mazon.mongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository repository;

    public List<Post> findAll(){
        return repository.findAll(Sort.by("date").descending());
    }

    public Post findById(String id){
        return repository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Post insert(Post post) {
        return repository.insert(post);
    }

    public void delete(String id){
        findById(id);
        repository.deleteById(id);
    }

    public Post update(Post post){
        Optional<Post> newPost = repository.findById(post.getId());
        updateData(newPost,post);
        return repository.save(newPost.get());
    }

    private void updateData(Optional<Post> newPost, Post post) {
        newPost.get().setTitle(post.getTitle());
        newPost.get().setBody(post.getBody());
        newPost.get().setAuthor(post.getAuthor());
        newPost.get().setDate(new Date());
    }

    public User fromDto(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }
}
