package com.cx.chapter9sdn.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * @author xi.cheng
 */
@NodeEntity(label = "User")
public class User {

    // @Id
    // @GeneratedValue
    // private Long id;
    // @Index(unique = true)
    // private String userId;
    // private String name;
    // @Relationship(type = "IS_FRIEND_OF", direction = Relationship.DIRECTION)
    // private Set<User> friends;
    // private User referredBy;
    //
    // public String getUserId() {
    //     return userId;
    // }
    //
    // public void setUserId(String userId) {
    //     this.userId = userId;
    // }
    //
    // public String getName() {
    //     return name;
    // }
    //
    // public void setName(String name) {
    //     this.name = name;
    // }
    //
    // public Set<User> getFriends() {
    //     return friends;
    // }
    //
    // public void setFriends(Set<User> friends) {
    //     this.friends = friends;
    // }
    //
    // public Long getId() {
    //     return id;
    // }
    //
    // public void setId(Long id) {
    //     this.id = id;
    // }
    //
    // public User getReferredBy() {
    //     return referredBy;
    // }
    //
    // public void setReferredBy(User referredBy) {
    //     this.referredBy = referredBy;
    // }
}
