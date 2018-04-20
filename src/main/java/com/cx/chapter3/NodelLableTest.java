package com.cx.chapter3;

import com.cx.common.Constant;
import com.cx.common.MyLables;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 节点标签 3.3
 *  标签是Neo4j的一个很好的补充, 不仅仅是一个分类的策略. 因为节点可以有很多标签, 所以可以创建标签
 *  把经常一起使用的节点分组(即使不同的类型也可以), 从而不必使用属性. 例如 RED_THINGS(对所有红色的节点)、
 *  FLYING(对所有代表能飞的节点)等
 * @author xi.cheng
 * @see Neo4jJavaApiTest
 */
public class NodelLableTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodelLableTest.class);

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
        try (Transaction tx = graphDb.beginTx()) {
            getNodeByIdTest();
            // 添加标签
            addLableTest();

            // 查找给定标签和属性的节点
            // findNodesByLabelAndPerpertyTest();
            tx.success();
        }
    }

    /**
     * @see Neo4jJavaApiTest#createNodeTest()
     */
    public void getNodeByIdTest() {
        user1 = graphDb.getNodeById(0L);
        user2 = graphDb.getNodeById(1L);
        user3 = graphDb.getNodeById(2L);
        movie1 = graphDb.getNodeById(3L);
        movie2 = graphDb.getNodeById(4L);
        movie3 = graphDb.getNodeById(5L);
    }

    // 给节点添加标签
    public void addLableTest() {
        // 要给加点添加标签,应该对选定的节点使用addLable()方法
        user1.addLabel(MyLables.USER);
        user2.addLabel(MyLables.USER);
        user3.addLabel(MyLables.USER);
        movie1.addLabel(MyLables.MOVIE);
        movie2.addLabel(MyLables.MOVIE);
        movie3.addLabel(MyLables.MOVIE);

        // 查找所有具有特定标签的节点
        // ResourceIterator<Node> movies = graphDb.findNodes(MyLables.MOVIE);
    }

    // 查找给定标签和属性的节点
    private void findNodesByLabelAndPerpertyTest() {
        /*
        默认情况下,Neo4j引擎通过便签和属性查找节点是以忙里执行的
        (通过循环查找所有具有给定标签的节点, 并比较需要的属性名和值).
        但是,如果对便签和属性定义了模式索引, nEO4J引擎将会使用更快的索引查找
        使用模式索引将在第5章中讨论
         */
        ResourceIterator<Node> nodes = graphDb.findNodes(MyLables.MOVIE, "name", "头号玩家");

    }
}
