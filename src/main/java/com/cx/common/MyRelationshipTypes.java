package com.cx.common;

import org.neo4j.graphdb.RelationshipType;

/**
 * 关系类型枚举类
 * 关系是图形数据库除节点以外的另外主要实体.每个关系都有一个名字,名字代表标签或者关系的类型
 * @author xi.cheng
 */
public enum MyRelationshipTypes implements RelationshipType {
    IS_FRIEND_OF,
    HAS_SEEN,
    KNOWS
}
