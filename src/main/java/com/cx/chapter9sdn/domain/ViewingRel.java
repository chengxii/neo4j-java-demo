package com.cx.chapter9sdn.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * @author xi.cheng
 */
@NodeEntity(label = "ViewingRel")
@RelationshipEntity(type = "HAS_SEEN")
public class ViewingRel {

    @Id
    @GeneratedValue
    private Long id;
    @StartNode
    private User user;
    @EndNode
    private Movie movie;
    private Integer starts;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getStarts() {
        return starts;
    }

    public void setStarts(Integer starts) {
        this.starts = starts;
    }
}
