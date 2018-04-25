package com.cx.chapter9sdn.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.Id;

/**
 * @author xi.cheng
 */
@NodeEntity(label = "Movie")
public class Movie {
//     @Id
//     @GeneratedValue
//     private Long id;
//     private String title;
//     // 从Movie节点的角度则不可以修改views的属性
//     // @Relationship(type = "HAS_SEEN")
//     // private Iterable<ViewingRel> views;
//     // public String getTitle() {
//     //     return title;
//     // }
//
//     public void setTitle(String title) {
//         this.title = title;
//     }
//     public Long getId() {
//         return id;
//     }
//     public void setId(Long id) {
//         this.id = id;
//     }
//     public String getTitle() {
//         return title;
//     }
// // public Iterable<ViewingRel> getViews() {
//     //     return views;
//     // }
//     //
//     // public void setViews(Iterable<ViewingRel> views) {
//     //     this.views = views;
//     // }
}
