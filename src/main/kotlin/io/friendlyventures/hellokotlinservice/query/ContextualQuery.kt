package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import io.friendlyventures.hellokotlinservice.context.MyGraphQLContext
import io.friendlyventures.hellokotlinservice.model.ContextualResponse
import org.springframework.stereotype.Component

/**
 * Example usage of [GraphQLContext] annotation. By using this annotation context parameter won't be exposed as in the
 * schema and will be automatically autowired at runtime using value from the environment.
 *
 * @see com.expediagroup.graphql.examples.context.MyGraphQLContextWebFilter
 * @see com.expediagroup.graphql.execution.FunctionDataFetcher
 */
@Component
class ContextualQuery : Query {

    @GraphQLDescription("query that uses GraphQLContext context")
    fun contextualQuery(
        @GraphQLDescription("some value that will be returned to the user")
        value: Int,
        context: MyGraphQLContext
    ): ContextualResponse = ContextualResponse(value, context.myCustomValue)
}
