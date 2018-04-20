package com.cx.chapter3;


import com.cx.common.Constant;
import com.cx.common.MyLables;
import com.cx.common.MyRelationshipTypes;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 第三章 - 原生java-API
 * 3.2
 *
 * @author xi.cheng
 */
public class Neo4jJavaApiTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jJavaApiTest.class);

    // 创建Neo4j数据库
    GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(Constant.PATH));

    private Node user1;
    private Node user2;
    private Node user3;
    private Node movie1;
    private Node movie2;
    private Node movie3;

    @Test
    public void fun() {
        // 创建事务
        try (Transaction tx = graphDb.beginTx()) {
            // 创建节点--执行一次
            createNodeTest();

            // 创建关系
            createRelationshipTest();

            // 为节点添加属性
            addPropertyToNode();

            // 节点类型
            setNodeTypeTest();

            // 为关系添加属性
            addPropertyToRelationship();

            // 提交事务
            tx.success();
        }
    }

    /**
     * 创建节点
     */
    public void createNodeTest() {
        // 事先先清空数据库的文件夹
        ResourceIterator<Node> nodes = graphDb.findNodes(MyLables.USER);
        if (!nodes.hasNext()) {
            user1 = graphDb.createNode();
            LOGGER.info("创建用户:" + user1.getId());
            user2 = graphDb.createNode();
            LOGGER.info("创建用户:" + user2.getId());
            user3 = graphDb.createNode();
            LOGGER.info("创建用户:" + user3.getId());

            movie1 = graphDb.createNode();
            LOGGER.info("创建电影:" + movie1.getId());
            movie2 = graphDb.createNode();
            LOGGER.info("创建电影:" + movie2.getId());
            movie3 = graphDb.createNode();
            LOGGER.info("创建电影:" + movie3.getId());
        }else {
            user1 = graphDb.getNodeById(0L);
            user2 = graphDb.getNodeById(1L);
            user3 = graphDb.getNodeById(2L);
            movie1 = graphDb.getNodeById(3L);
            movie2 = graphDb.getNodeById(4L);
            movie3 = graphDb.getNodeById(5L);
        }
    }

    /**
     * 创建关系
     */
    public void createRelationshipTest() {
        // 用户与用户之间建立 IS_FRIEND_OF的关系
        user1.createRelationshipTo(user2, MyRelationshipTypes.IS_FRIEND_OF);
        user1.createRelationshipTo(user3, MyRelationshipTypes.IS_FRIEND_OF);

        // 只有在运行是才能知道关系类型,可以使用动态关系
        // String runtimeVal = "IS_FRIEND_OF";
        // RelationshipType rel = RelationshipType.withName(runtimeVal);
    }

    /**
     * 3.2.3 为节点添加属性
     * 在Neo4j中,必须给添加的每个属性这是属性值. 换句话说,Neo4j不允许null属性值
     * 如果需要从节点上删除属性, 必须明确的使用Neo4j核心Java API : Node:removeProperty(String propertyName); 删除
     */
    public void addPropertyToNode() {
        user1.setProperty("name", "程希");
        user1.setProperty("year_of_birth", 1993);
        user2.setProperty("name", "陈浩");
        user2.setProperty("locked", true);
        user3.setProperty("name", "辉哥");
        user3.setProperty("cars_owned", new String[]{"红旗", "解放牌拖拉机"});
    }

    /**
     * 3.2.4 节点类型策略
     */
    public void setNodeTypeTest() {
        user1.setProperty("type", "User");
        user2.setProperty("type", "User");
        user3.setProperty("type", "User");

        movie1.setProperty("name", "头号玩家");
        movie2.setProperty("name", "大雄的南极冒险");
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
    }

    /**
     * 3.2.5 为关系添加属性
     * 创建具有属性的关系
     */
    public void addPropertyToRelationship() {
        Relationship rel1 = user1.createRelationshipTo(movie1, MyRelationshipTypes.HAS_SEEN);
        rel1.setProperty("starts", 5);
        Relationship rel2 = user2.createRelationshipTo(movie3, MyRelationshipTypes.HAS_SEEN);
        rel2.setProperty("starts", 3);
        Relationship rel3 = user3.createRelationshipTo(movie1, MyRelationshipTypes.HAS_SEEN);
        rel3.setProperty("starts", 4);
        Relationship rel4 = user3.createRelationshipTo(movie2, MyRelationshipTypes.HAS_SEEN);
        rel4.setProperty("starts", 5);
    }
}
