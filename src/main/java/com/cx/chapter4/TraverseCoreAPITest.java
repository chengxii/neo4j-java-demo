package com.cx.chapter4;

import com.cx.common.Constant;
import com.cx.common.MyRelationshipTypes;
import org.junit.Test;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * 4.1 使用Neo4j核心Java API 进行遍历
 * @author xi.cheng
 */
public class TraverseCoreAPITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraverseCoreAPITest.class);

    GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(Constant.PATH));

    public static Long CX_NODE_ID = 0L;
    Node cxNode;

    @Test
    public void fun() {
        try (Transaction tx = graphDb.beginTx()) {
            // 获取节点
            cxNode = getCxNode();

            // 找到 程希 已经看过的所有电影
            getCxHasSeenMovies();

            // 找到 程希 已经看过的所有电影-增强
            getCxHasSeenMoviesPro();

            // 查找程希的朋友喜欢而程希还没有看过的电影
            recommendMoviesTest();

            // 使用迭代接口降低Java堆内存的消耗
            System.out.println("--------");
            recommendMoviesProTest();
            tx.success();
        }
    }

    /**
     * 4.1.1　寻找起始节点
     * 理想情况下，我们将基于一些属性值而不是节点ID寻找起始节点，例
     * 如，通过姓名或者电子邮件地址找到user节点。Neo4j的临时节点查找是使
     * 用索引执行的，这将在第5章中详细讨论。在本章中，节点的查找将以节点
     * 的ID操作。
     */
    private Node getCxNode() {
        // 通过节点的标识符从Neo4j数据库中加载节点对象
        Node node = graphDb.getNodeById(CX_NODE_ID);
        LOGGER.info("根据节点ID查找节点--->" + node.getProperty("name"));
        return node;
    }

    /**
     * 找到 程希 已经看过的所有电影
     */
    private void getCxHasSeenMovies() {
        // 遍历直接关系. 使用getRelationships()得到始于或终于给定节点所有的关系
        Iterable<Relationship> allRelationships = cxNode.getRelationships();
        HashSet<Node> moviesForCx = new HashSet<>();
        for (Relationship r : allRelationships) {
            // 通过检查每一个关系类型过滤HAS_SEEN关系
            if (r.getType().name().equalsIgnoreCase(MyRelationshipTypes.HAS_SEEN.toString())){
                // 得到关系的结束节点,改节点在本模型中代表电影
                Node movieNode = r.getEndNode();
                moviesForCx.add(movieNode);
            }
        }
        // 在控制台输出结果节点
        for (Node movie : moviesForCx) {
            LOGGER.info("程希看过的电影:  " + movie.getProperty("name"));
        }
    }


    /**
     * 找到 程希 已经看过的所有电影(功能改进)
     * 用名字过滤关系是图形遍历
     * @see IterableTest#getCxHasSeenMovies()
     */
    private void getCxHasSeenMoviesPro() {
        // 使用getRelationships(RelationshipType type)方法获得指定类型(起始或终止于给定节点)的所有关系
        Iterable<Relationship> allRelationships = cxNode.getRelationships(MyRelationshipTypes.HAS_SEEN);
        HashSet<Node> moviesForCx = new HashSet<>();
        for (Relationship r : allRelationships) {
            // 得到关系的结束节点,改节点在本模型中代表电影
            Node movieNode = r.getEndNode();
            moviesForCx.add(movieNode);
        }
        // 在控制台输出结果节点
        for (Node movie : moviesForCx) {
            LOGGER.info("程希看过的电影:  " + movie.getProperty("name"));
        }
    }

    /**
     * 4.1.3  遍历深度为2的关系
     * 查找程希的朋友喜欢而程希还没有看过的电影
     *      为了解决这个问题，我们将把它分解为几步。首先，我们从起始节点开
     * 始按照IS_FRIEND_OF关系查找 程希 的所有朋友。其次，从代表朋友的节点
     * 开始沿着HAS_SEEN关系查找他们已经看过的电影。
     */
    private void recommendMoviesTest() {
        // 查找 程希 的朋友已经看过的电影
        // 从关系得到 程希 的朋友
        HashSet<Node> friends = new HashSet<>();
        for (Relationship r1 : cxNode.getRelationships(MyRelationshipTypes.IS_FRIEND_OF)) {
            // 给定关系的一端的出另外一端
            Node friend = r1.getOtherNode(cxNode);
            friends.add(friend);
        }
        Set<Node> moviesFriendsLike = new HashSet<>();
        for (Node friend : friends) {
            for (Relationship r2
                    : friend.getRelationships(Direction.OUTGOING, MyRelationshipTypes.HAS_SEEN)) { // 对每一个用户节点,迭代向外的HAS_SEEN关系
                moviesFriendsLike.add(r2.getEndNode());
            }
        }
        // 输出结果
        for (Node movie : moviesFriendsLike) {
            LOGGER.info("程希 的朋友已经看过的电影: " + movie.getProperty("name"));
        }

        Set<Node> moviesCxLike = new HashSet<>();
        // 查找程希看过的所有电影
        for (Relationship relationship : cxNode.getRelationships(Direction.OUTGOING, MyRelationshipTypes.HAS_SEEN)) {
            moviesCxLike.add(relationship.getEndNode());
        }
        // 从结果中删除程希看过的所有电影
        moviesFriendsLike.removeAll(moviesCxLike);
        // 打印结果
        for (Node movie : moviesFriendsLike) {
            LOGGER.info("推荐电影: " + movie.getProperty("name"));
        }
    }

    /**
     * 使用迭代接口降低Java堆内存的消耗
     *
     * 这个解决方案看上去比较好，但是，当面对有用户看过的几百万部电影
     * 时，存在一个潜在的瓶颈（因为在每一次循环中都必须检查John是否已经看
     * 过）。在有限的数据集中，这不会有什么影响，但是，必须指出的是，当面
     * 对大数据集时，编写简单的代码有时会更好，即使多写一点也没关系。在本
     * 例中，一旦往前查找，就会找到John看过的所有电影，然后用这个数据集在
     * for循环中过滤电影
     *
     * Neo4j核心Java API允许我们访问底层Noe4j数
     * 据结构以实现需要的遍历，但是存在付出多写代码的代价并担心其他的方面
     * （如内存的消耗）。幸运的是Neo4j配备了一个高精炼的API，可以满足以简
     * 单、自然的方式描述遍历的需求
     */
    private void recommendMoviesProTest() {
        HashSet<Node> moviesFriendsLike = new HashSet<>();
        for (Relationship r1 : cxNode.getRelationships(MyRelationshipTypes.IS_FRIEND_OF)) {
            // 给定关系的一端的出另外一端
            Node friend = r1.getOtherNode(cxNode);
            for (Relationship r2 : friend.getRelationships(Direction.OUTGOING, MyRelationshipTypes.HAS_SEEN)) {
                Node movie = r2.getEndNode();
                boolean cxLikesIt = false;
                for (Relationship r3 : movie.getRelationships(Direction.INCOMING, MyRelationshipTypes.HAS_SEEN)) {
                    if (r3.getStartNode().equals(cxNode)) {
                        cxLikesIt = true;
                    }
                }
                if (!cxLikesIt) {
                    moviesFriendsLike.add(movie);
                }
            }
        }
        // 打印结果
        for (Node movie : moviesFriendsLike) {
            LOGGER.info("推荐电影: " + movie.getProperty("name"));
        }
    }

}
