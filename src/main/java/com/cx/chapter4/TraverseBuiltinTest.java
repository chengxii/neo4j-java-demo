package com.cx.chapter4;

import com.cx.common.Constant;
import com.cx.common.MyRelationshipTypes;
import org.junit.Test;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 4.2　使用Neo4j的遍历API进行遍历
 *      使用Neo4j的内置遍历结构
 *
 *      Neo4j遍历API是一个具有流畅创建器API且基于回调函数的框架，它允
 * 许你在一行代码中以可表达的方式创建遍历规则。遍历API的主要部分是
 * org.neo4j.graphdb.traversal.TraversalDescription接口，它定义了创建器的方法
 * 用于描述遍历器行为。你可以将遍历比作一个机器人，按照一组定义好的遍
 * 历顺序规则和跟踪关系、在结果中需要包含的关系和节点等，通过关系从一
 * 个节点跳跃到另一个节点。Traversal Description（遍历描述）是一个不可变
 * 的对象，用于定义遍历规则。添加任何新的遍历规则（在
 * TraversalDescription接口中调用创建器方法中的任何一个）总是返回新的TraversalDescription实例。
 * @author xi.cheng
 */
public class TraverseBuiltinTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraverseBuiltinTest.class);

    GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(Constant.PATH));

    public static Long CX_NODE_ID = 0L;
    Node cxNode;

    @Test
    public void fun() {
        try (Transaction tx = graphDb.beginTx()) {
            // 获取节点
            cxNode = getCxNode();

            // 查找朋友看过的电影
            traverseFriendsHasSeenMovieTest();

            tx.success();
        }
    }

    private Node getCxNode() {
        // 通过节点的标识符从Neo4j数据库中加载节点对象
        Node node = graphDb.getNodeById(CX_NODE_ID);
        LOGGER.info("根据节点ID查找节点--->" + node.getProperty("name"));
        return node;
    }

    /**
     * 使用Neo4j遍历API查找朋友看过的电影
     */
    private void traverseFriendsHasSeenMovieTest() {
        // 实例化遍历描述
        TraversalDescription traversalMoviesFriendsLikeTraversal = graphDb.traversalDescription()
                .relationships(MyRelationshipTypes.IS_FRIEND_OF)    // 添加 IS_FRIEND_OF关系到跟踪关系的列表中
                .relationships(MyRelationshipTypes.HAS_SEEN, Direction.OUTGOING)
                .uniqueness(Uniqueness.NODE_GLOBAL)     // 设置唯一性是结果中每一个节点是唯一的
                .evaluator(Evaluators.atDepth(2))  // 设置评估函数使他与深度2处的节点匹配 -- 存在的评估函数人在用
                .evaluator(new CustomNodeFilteringEvaluator(cxNode));// 自定义函数加到遍历定义中
        // 代表从用户 程希 的节点开始遍历
        Traverser traverser = traversalMoviesFriendsLikeTraversal.traverse(cxNode);
        Iterable<Node> moviesFriendsLike = traverser.nodes();
        for (Node movie : moviesFriendsLike) {
            LOGGER.info("查找电影: " + movie.getProperty("name"));
        }
    }

}
