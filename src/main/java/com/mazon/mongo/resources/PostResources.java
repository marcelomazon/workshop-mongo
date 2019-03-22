package com.mazon.mongo.resources;

import com.mazon.mongo.domain.Post;
import com.mazon.mongo.resources.util.URL;
import com.mazon.mongo.services.PostService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
@Api(tags = "Postagens",description = "Postagens do blog", value = "Postagens") // para documentação do swagger
public class PostResources {

    @Autowired
    private PostService service;

    @GetMapping
    @ApiOperation("Buscar todas as postagens")
    public ResponseEntity<List<Post>> findAll() {

        List<Post> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar postagem por id")
    public ResponseEntity<Post> findById(
            @ApiParam(value = "Id do post a ser listado", required = true) @PathVariable(value = "id") String id) {
        Post post = service.findById(id);
        return ResponseEntity.ok().body(post);
    }

    @PostMapping
    @ApiOperation(value = "Inserir nova postagem", notes = "Os posts são conteúdos publicados no blog (postagens), " +
            "em ordem cronológica. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean a ante vehicula, " +
            "vehicula tortor et, ultrices nibh. Etiam blandit felis eu lectus tempor, vel congue diam pretium. " +
            "Aenean ultrices est eget mi rutrum porta")
    public ResponseEntity<Void> insert(@RequestBody Post post) {

        Post obj = service.insert(post);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
        // created retorna o código 201 quando um novo recurso é gerado
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir uma postagem")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Não é possível excluir uma postagem que possui comentário"),
            @ApiResponse(code = 404, message = "Id da postagem não encontrado") })
    public ResponseEntity<Void> delete(
            @ApiParam(value = "Id da postagem para exclusão", required = true) @PathVariable(value = "id") String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar uma postagem")
    public ResponseEntity<Post> update(
            @ApiParam(value = "Id da postagem", required = true) @RequestBody Post post, @PathVariable(value = "id") String id) {
        post.setId(id);
        post = service.update(post);
        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/titlesearch")
    @ApiOperation(value = "Buscar postagens pelo título")
    public ResponseEntity<List<Post>> findByTitle(
            @ApiParam(value = "Texto para a busca (case insensitive)") @RequestParam(value = "text", defaultValue = "") String text) {
        text = URL.decodeParam(text);
        System.out.println(text);
        List<Post> list = service.findByTitle(text);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/bodysearch")
    @ApiOperation(value = "Buscar postagens pelo texto")
    public ResponseEntity<List<Post>> findByBoody(
            @ApiParam(value = "Texto para buscar no body da postagem") @RequestParam(value = "body", defaultValue = "") String body) {
        body = URL.decodeParam(body);
        System.out.println(body);
        List<Post> list = service.findByBody(body);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/consulta")
    @ApiOperation(value="Consulta avançada de postagens")
    public ResponseEntity<List<Post>> consultaCompleta(
            @ApiParam(value = "Texto que será consultado nas postagem", required = true) @RequestParam(value = "text") String text,
            @ApiParam(value = "Filtra as postagens a partir da data mínima") @RequestParam(value = "minDate", defaultValue = "") String minDate,
            @ApiParam(value = "Filtra as postagens até a data máxima") @RequestParam(value = "maxDate", defaultValue = "") String maxDate) {
        text = URL.decodeParam(text);
        Date min = URL.converDate(minDate, new Date(0L));
        Date max = URL.converDate(maxDate, new Date());

        List<Post> list = service.consultaCompleta(text, min, max);
        return ResponseEntity.ok().body(list);
    }
}
