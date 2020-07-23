package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import io.friendlyventures.hellokotlinservice.model.Node
import org.springframework.stereotype.Component

@Component
class RecursiveQuery : Query {

    private final val root = Node(id = 0, value = "root", parent = null, children = emptyList())
    private final val nodeA = Node(id = 1, value = "A", parent = root, children = emptyList())
    private final val nodeB = Node(id = 2, value = "B", parent = root, children = emptyList())
    private final val nodeC = Node(id = 3, value = "C", parent = nodeB, children = emptyList())

    init {
        root.children = listOf(nodeA, nodeB)
        nodeB.children = listOf(nodeC)
    }

    @GraphQLDescription("Returns the root of a node graph")
    fun nodeGraph(): Node = root
}
