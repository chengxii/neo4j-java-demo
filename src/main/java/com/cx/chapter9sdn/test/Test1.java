package com.cx.chapter9sdn.test;

import com.cx.chapter9sdn.domain.Movie;
import com.cx.chapter9sdn.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

/**
 * @author xi.cheng
 */
public class Test1 {

    @Autowired
    Neo4jRepository neo4jRepository;

    @Test
    public void fun() {
        saveAndLoad();
    }

    @Transactional
    public void saveAndLoad() {
        Movie movie = new Movie();
        movie.setTitle("变金刚");
        neo4jRepository.save(movie);
    }
}
