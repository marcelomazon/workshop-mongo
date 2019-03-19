package com.mazon.mongo.config;

import com.mazon.mongo.domain.Post;
import com.mazon.mongo.domain.User;
import com.mazon.mongo.dto.AuthorDto;
import com.mazon.mongo.repository.PostRepository;
import com.mazon.mongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll(); // deleta toda coleção
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, sdf.parse("18/03/2019"), "Partiu viagem","Vou viajar para SP - Abraço!", new AuthorDto(maria));
        Post post2 = new Post(null, sdf.parse("19/03/2019"), "Bom dia", "Esse spring é do carvalho", new AuthorDto(maria));

        postRepository.saveAll(Arrays.asList(post1, post2));
    }
}
