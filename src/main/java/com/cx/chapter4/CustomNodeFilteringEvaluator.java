package com.cx.chapter4;

import com.cx.common.MyRelationshipTypes;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

/**
 * 自定义评估函数用于排除用户看过的电影
 * @author xi.cheng
 */
public class CustomNodeFilteringEvaluator implements Evaluator {

    private final Node userNode;

    /**
     * 构造函数中为评估函数提供的其实用户节点
     * @param userNode
     */
    public CustomNodeFilteringEvaluator(Node userNode) {
        this.userNode = userNode;
    }

    @Override
    public Evaluation evaluate(Path path) {
        // 得到遍历中当前节点的引用
        Node currentNode = path.endNode();
        if (!currentNode.hasProperty("type") ||
                !currentNode.getProperty("type").equals("Movie")) {
            return Evaluation.EXCLUDE_AND_CONTINUE;
        }
        // 通过当前电影节点所有向内的HAS_SEEN关系迭代
        for (Relationship r : currentNode.getRelationships(Direction.INCOMING, MyRelationshipTypes.HAS_SEEN)) {
            if (r.getStartNode().equals(userNode)) {
                // 如果关系的其实节点与用户节点相同,丢弃当前节点(因为用户已经看过)并继续
                return Evaluation.EXCLUDE_AND_CONTINUE;
            }
        }
        // 否则, 包含结果中的当前节点并继续
        return Evaluation.INCLUDE_AND_CONTINUE;
    }
}
