package com.cx.chapter9sdn.repo;

import com.cx.chapter9sdn.domain.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author xi.cheng
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {
}
