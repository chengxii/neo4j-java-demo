package com.cx.chapter9sdn.conf;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xi.cheng
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.cx.chapter9sdn")
@EnableNeo4jRepositories("com.cx.chapter9sdn.repo")
public class MyConfiguration {

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(configuration(), "com.cx.chapter9sdn.domain");
    }

    @Bean
    public Neo4jTransactionManager transactionManager() throws Exception {
        return new Neo4jTransactionManager(getSessionFactory());
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://localhost")
                .credentials("neo4j", "root")
                .build();
    }
}
