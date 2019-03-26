package com.mazon.mongo.repository;

import com.mazon.mongo.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findByTitleContainingIgnoreCase(String text);

    // procura na propriedade body a expressão enviada em text.
    // a option "i" indica que o tipo é case insentive
    @Query("{ 'body': { $regex: ?0, $options: 'i' } }")
    List<Post> findByBody(String body);

    @Query("{ $and: [ {date: {$gte: ?1} }, {date: { $lte: ?2}}, { $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } }, { 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
    List<Post> consultaCompleta(String text, Date minDate, Date maxDate);
}