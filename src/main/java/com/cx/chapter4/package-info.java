package com.cx.chapter4;
/**
 强大的图形遍历功能:
    使用核心Java API查询Neo4j数据库
    应用Neo4j的遍历API遍历节点和关系
    扩展和定制Neo4j的内置遍历操作


 Node.getRelationships（...）方法返回了Neo4j的Iterable实现，
 org.neo4j.kernel.impl.util.ArrayIntIterator既实现了Java Iterable接口，也实现了
 Iterator接口，并且是一个围绕java.util.Iterator的瘦包装类。在对结果迭代之
 前实际上还没有访问结果中包含的元素。这一实现是在第一次访问时延迟加
 载的，一旦使用，就不能再次使用——变为无效（这是Java迭代器期望的行
 为）。

 当使用Neo4j核心Java API时，这是非常重要的。Iterable接口允许Neo4j
 返回非常大的数据集，开发者负责确保以正确的方式访问数据集中的元素。
 就像我们说过的，如果从Neo4j的Iterable接口加载大量的结果到你自己的
 Java list或set中，必须意识到你的程序代码需要大量的Jave堆内存，这样在
 运行程序时就会有可能出现OutOfMemoryError异常。这就是为什么尽可能在
 程序代码中使用Iterable接口被认为是一个好的做法，而不是将其转换为大
 量需要内存结构的代码


 遍历图形时，Neo4j核心Java API功能强大并且灵活性好，
 但是，对于复杂的遍历，Neo4j核心Java API的实现将很快变得复杂起来。


 在Neo4j遍历API
 中，可以同时建立多个评估函数，因此可以添加多个评估函数到单个遍历描
 述（Travelsal Description）中。如果在遍历的过程中包含多个评估函数，那
 么使用布尔代数：对包含在结果中的当前节点，所有的评估函数都应该返回
 带一个INCLUDE元素（INCLUDE_AND_CONTINUE或
 INCLUDE_AND_PRUNE）的评估值。类似地，要沿着同一路径继续往下遍
 历，所有的评估函数都要返回一个CONTINUE评估函数
 （INCLUDE_AND_CONTINUE或EXCLUDE_AND_CONTINUE）。

 如果使用的是Java API，建议
 在可能的情况下使用Neo4j遍历器API框架遍历做图形遍历，因为Neo4j遍历
 器API框架在处理复杂图形遍历时产生可读的、可维护的和高性能的代码。

 后面会讨论更加先进的遍历概念
 */