package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import io.friendlyventures.hellokotlinservice.model.HidesInheritance
import org.springframework.stereotype.Component

@Component
class PrivateInterfaceQuery : Query {

    @GraphQLDescription("this query returns class implementing private interface which is not exposed in the schema")
    fun queryForObjectWithPrivateInterface(): HidesInheritance = HidesInheritance(id = 123)
}
