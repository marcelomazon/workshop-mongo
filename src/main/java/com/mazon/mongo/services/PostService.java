package com.mazon.mongo.services;

import com.mazon.mongo.domain.Post;
import com.mazon.mongo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository repository;

    public List<Post> findAll(){
        return repository.findAll(Sort.by("date").descending());
    }
}
