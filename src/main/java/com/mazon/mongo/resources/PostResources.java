package com.mazon.mongo.resources;

import com.mazon.mongo.domain.Post;
import com.mazon.mongo.resources.util.URL;
import com.mazon.mongo.services.PostService;
import javafx.beans.binding.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostResources {

    @Autowired
    private PostService service;

    @GetMapping
    public ResponseEntity<List<Post>> findAll() {

        List<Post> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable(value = "id") String id) {
        Post post = service.findById(id);
        return ResponseEntity.ok().body(post);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Post post) {

        Post obj = service.insert(post);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
        // created retorna o código 201 quando um novo recurso é gerado
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@RequestBody Post post, @PathVariable(value = "id") String id) {
        post.setId(id);
        post = service.update(post);
        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
        text = URL.decodeParam(text);
        System.out.println(text);
        List<Post> list = service.findByTitle(text);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/bodysearch")
    public ResponseEntity<List<Post>> findByBoody(@RequestParam(value = "body", defaultValue = "") String body) {
        body = URL.decodeParam(body);
        System.out.println(body);
        List<Post> list = service.findByBody(body);
        return ResponseEntity.ok().body(list);
    }

    /**
     * Faz uma busca em nos campos text e body do post e comentarios
     * considerando um intervalo de datas
     * Se não informar as datas, busca todos os posts até a data atual
     *
     * @param text  texto para busca
     * @param minDate data inicial para filtro
     * @param maxDate data final para filtro
     * @return lista de Posts
     */
    @GetMapping("/consulta")
    public ResponseEntity<List<Post>> consultaCompleta(
            @RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(value = "minDate", defaultValue = "") String minDate,
            @RequestParam(value = "maxDate", defaultValue = "") String maxDate) {
        text = URL.decodeParam(text);
        Date min = URL.converDate(minDate, new Date(0L));
        Date max = URL.converDate(maxDate, new Date());

        List<Post> list = service.consultaCompleta(text, min, max);
        return ResponseEntity.ok().body(list);
    }
}
