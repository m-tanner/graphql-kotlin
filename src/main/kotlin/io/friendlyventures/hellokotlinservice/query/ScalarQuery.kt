package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import java.util.*

/**
 * Simple query that exposes custom scalar.
 */
@Component
class ScalarQuery : Query {

    @GraphQLDescription("generates random UUID")
    fun generateRandomUUID() = UUID.randomUUID()

    @GraphQLDescription("Prints a string with a custom scalar as input")
    fun printUuids(uuids: List<UUID>) = "You sent $uuids"

    fun findPersonById(id: ID) = Person(id, "Nelson")

    @GraphQLDescription("generates random GraphQL ID")
    fun generateRandomId() = ID(UUID.randomUUID().toString())
}

@Component
class ScalarMutation : Mutation {
    fun addPerson(person: Person): Person = person
}

data class Person(
    val id: ID,
    val name: String
)
