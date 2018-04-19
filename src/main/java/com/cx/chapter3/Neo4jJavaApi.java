package com.cx.chapter3;


import com.cx.common.Constant;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 第三章 - 原生java-API
 * @author xi.cheng
 */
public class Neo4jJavaApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jJavaApi.class);

    /**
     * 创建节点
     */
    @Test
    public void createNodeTest() {
        // 创建Neo4j数据库
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(Constant.PATH));
        // 创建事务
        try (Transaction tx = graphDb.beginTx()) {
            Node user1 = graphDb.createNode();
            LOGGER.info("create user:" + user1.getId());
            Node user2 = graphDb.createNode();
            LOGGER.info("create user:" + user2.getId());
            Node user3 = graphDb.createNode();
            LOGGER.info("create user:" + user3.getId());
            // 提交事务
            tx.success();
        }
    }

    /**
     * 创建关系
     */
    @Test
    public void createRelationshipTest() {
        // 创建Neo4j数据库
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(Constant.PATH));
        // 创建事务
        try (Transaction tx = graphDb.beginTx()) {
            Node user1 = graphDb.createNode();
            Node user2 = graphDb.createNode();
            Node user3 = graphDb.createNode();
            // 用户与用户之间建立 IS_FRIEND_OF的关系
            user1.createRelationshipTo(user2, MyRelationshipTypes.IS_FRIEND_OF);
            user1.createRelationshipTo(user3, MyRelationshipTypes.IS_FRIEND_OF);

            // 只有在运行是才能知道关系类型,可以使用动态关系
            // String runtimeVal = "IS_FRIEND_OF";
            // RelationshipType rel = RelationshipType.withName(runtimeVal);
            tx.success();
        }
    }

    /**
     * 3.2.3 为节点添加属性
     * 在Neo4j中,必须给添加的每个属性这是属性值. 换句话说,Neo4j不允许null属性值
     * 如果需要从节点上删除属性, 必须明确的使用Neo4j核心Java API : Node:removeProperty(String propertyName); 删除
     */
    @Test
    public void addPropertyToNode() {
        // 创建Neo4j数据库
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(Constant.PATH));
        // 创建事务
        try (Transaction tx = graphDb.beginTx()) {
            Node user1 = graphDb.createNode();
            Node user2 = graphDb.createNode();
            Node user3 = graphDb.createNode();
            user1.setProperty("name", "程希");
            user1.setProperty("year_of_birth", 1993);
            user2.setProperty("name", "陈浩");
            user2.setProperty("locked", true);
            user3.setProperty("cars_owned", new String[]{"BMW", "AUDI"});
            tx.success();
        }
    }

    /**
     * 3.2.4 节点类型策略
     */
    @Test
    public void NodeTypeTest() {
        // 创建Neo4j数据库
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(Constant.PATH));
        // 创建事务
        try (Transaction tx = graphDb.beginTx()) {
            Node user1 = graphDb.createNode();
            Node user2 = graphDb.createNode();
            Node user3 = graphDb.createNode();
            user1.setProperty("type", "User");
            user2.setProperty("type", "User");
            user3.setProperty("type", "User");

            Node movie1 = graphDb.createNode();
            movie1.setProperty("name", "头号玩家");
            Node movie2 = graphDb.createNode();
            movie2.setProperty("name", "大雄的南极冒险");
            Node movie3 = graphDb.createNode();
            movie3.setProperty("name", "变形金刚");
            movie1.setProperty("type", "Movie");
            movie2.setProperty("type", "Movie");
            movie3.setProperty("type", "Movie");
            // 有了节点的类型, 可以简单的检查节点的属性值以确定节点的类型
            // if ("Movie".equals(node.getProperty("type"))) {
            //     // (这是一部电影)
            // }
            // if ("User".equals(node.getProperty("type"))) {
            //     // (这是一个用户)
            // }
            tx.success();
        }
    }

    /**
     * 3.2.5 为关系添加属性
     * 创建具有属性的关系
     */
    @Test
    public void addPropertyToRelationship() {
        // 创建Neo4j数据库
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(Constant.PATH));
        // 创建事务
        try (Transaction tx = graphDb.beginTx()) {
            Node user1 = graphDb.createNode();
            Node user2 = graphDb.createNode();
            Node user3 = graphDb.createNode();
            user1.setProperty("type", "User");
            user2.setProperty("type", "User");
            user3.setProperty("type", "User");

            Node movie1 = graphDb.createNode();
            movie1.setProperty("name", "头号玩家");
            Node movie2 = graphDb.createNode();
            movie2.setProperty("name", "大雄的南极冒险");
            Node movie3 = graphDb.createNode();
            movie3.setProperty("name", "变形金刚");
            movie1.setProperty("type", "Movie");
            movie2.setProperty("type", "Movie");
            movie3.setProperty("type", "Movie");
            // 有了节点的类型, 可以简单的检查节点的属性值以确定节点的类型
            // if ("Movie".equals(node.getProperty("type"))) {
            //     // (这是一部电影)
            // }
            // if ("User".equals(node.getProperty("type"))) {
            //     // (这是一个用户)
            // }
            tx.success();
        }
    }

}
