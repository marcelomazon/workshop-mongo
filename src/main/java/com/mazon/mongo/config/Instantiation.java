package com.mazon.mongo.config;

import com.mazon.mongo.domain.Post;
import com.mazon.mongo.domain.User;
import com.mazon.mongo.dto.AuthorDto;
import com.mazon.mongo.dto.CommentDto;
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

        // criando usuários
        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        // criando posts para o blog
        Post post1 = new Post(null, sdf.parse("18/03/2019"), "Partiu viagem","Vou viajar para SP - Abraço!", new AuthorDto(maria));
        Post post2 = new Post(null, sdf.parse("19/03/2019"), "Bom dia", "Esse spring é do carvalho", new AuthorDto(maria));
        Post post3 = new Post(null, sdf.parse("20/03/2019"), "Vamos pedalar", "A vida é igual andar de bicicleta", new AuthorDto(bob));

        // comentários para os posts
        CommentDto c1 = new CommentDto("Boa viagem mano", sdf.parse("21/03/2018"), new AuthorDto(alex));
        CommentDto c2 = new CommentDto("Aproveite", sdf.parse("22/03/2018"), new AuthorDto(bob));
        CommentDto c3 = new CommentDto("tenha um otimo dia", sdf.parse("19/03/2018"), new AuthorDto(alex));

        // associando os comentários aos posts
        post1.getComments().addAll(Arrays.asList(c1, c2));
        post2.getComments().addAll(Arrays.asList(c3));

        postRepository.saveAll(Arrays.asList(post1, post2, post3));

        // adicionando os posts ao usuario
        maria.getPosts().addAll(Arrays.asList(post1, post2));
        userRepository.save(maria);

        bob.getPosts().addAll(Arrays.asList(post3));
        userRepository.save(bob);
    }
}
