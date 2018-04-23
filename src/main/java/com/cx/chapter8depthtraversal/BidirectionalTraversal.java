package com.cx.chapter8depthtraversal;

import com.cx.common.Constant;
import com.cx.common.MyRelationshipTypes;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.BidirectionalTraversalDescription;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;
import org.neo4j.graphdb.traversal.SideSelectorPolicies;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 8.4 双向遍历
 * @author xi.cheng
 */
public class BidirectionalTraversal {
    private static final Logger LOGGER = LoggerFactory.getLogger(BidirectionalTraversal.class);

    GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(Constant.PATH));

    private Node user1;
    private Node user2;
    @Before
    public void getNodeByIdTest() {
        user1 = graphDb.getNodeById(0L);
        user2 = graphDb.getNodeById(1L);
    }

    @Test
    public void fun() {
        bidirectionalTraversalTest();
    }

    private void bidirectionalTraversalTest() {
        // 初始化双向遍历描述
        BidirectionalTraversalDescription description = graphDb.bidirectionalTraversalDescription()
                // 设置遍历描述的其实侧为遍历出的方向
                .startSide(
                        graphDb.traversalDescription().relationships(MyRelationshipTypes.KNOWS).uniqueness(Uniqueness.NODE_PATH)
                )
                // 设置遍历描述的结束侧为遍历进的方向
                .endSide(
                        graphDb.traversalDescription().relationships(MyRelationshipTypes.KNOWS).uniqueness(Uniqueness.NODE_PATH)
                )
                // 设置碰撞评估函数为包含找到的所有碰撞点
                .collisionEvaluator(new Evaluator() {
                    @Override
                    public Evaluation evaluate(Path path) {
                        return Evaluation.INCLUDE_AND_CONTINUE;
                    }
                })
                // 设置侧选择器为在两个遍历方向之间交替变换
                .sideSelector(SideSelectorPolicies.ALTERNATING, 100);
        // 通过制定其实节点和结束节点开始双向遍历
        Traverser traverser = description.traverse(user1, user2);
        ResourceIterator<Path> iterator = traverser.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
