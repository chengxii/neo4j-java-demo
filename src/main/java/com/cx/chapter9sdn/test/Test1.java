package com.cx.chapter9sdn.test;

import com.cx.chapter9sdn.conf.MyConfiguration;
import com.cx.chapter9sdn.domain.Person;
import com.cx.chapter9sdn.repo.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xi.cheng
 */
@ContextConfiguration(classes = MyConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class Test1 {

    @Autowired
    Neo4jRepository neo4jRepository;
    @Autowired
    PersonRepository personRepository;

    @Test
    public void fun() {
        saveAndLoad();
    }

    @Transactional
    public void saveAndLoad() {
        // personRepository.save(new Person1("程希13123132123"));
        // personRepository.save(new Person("陈浩", 26));
        personRepository.save(new Person("陈浩1"));
    }
}
