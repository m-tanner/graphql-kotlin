package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import io.friendlyventures.hellokotlinservice.model.*
import org.springframework.stereotype.Component

/**
 * Example query that displays the usage of interfaces and polymorphism.
 */
@Component
class PolymorphicQuery : Query {

    @GraphQLDescription("this query returns specific animal type")
    fun animal(type: AnimalType): Animal? = when (type) {
        AnimalType.CAT -> Cat()
        AnimalType.DOG -> Dog()
    }

    fun dog(): Dog = Dog()

    @GraphQLDescription("this query can return either a RightHand or a LeftHand as part of the union of both type")
    fun whichHand(whichHand: String): BodyPart = when (whichHand) {
        "right" -> RightHand(12)
        else -> LeftHand("hello world")
    }

    @GraphQLDescription("Example of interfaces with sealed classes")
    fun getFruit(orange: Boolean) = if (orange) Fruit.Orange() else Fruit.Apple("granny smith")
}
