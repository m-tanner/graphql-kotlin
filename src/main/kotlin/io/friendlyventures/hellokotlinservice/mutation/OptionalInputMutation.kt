package io.friendlyventures.hellokotlinservice.mutation

import com.expediagroup.graphql.spring.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class OptionalInputMutation : Mutation {

    /**
     * Example of how to check the difference between a client not sending a value
     * or explicitly passing in null
     */
    fun optionalInput(value: String?, dataFetchingEnvironment: DataFetchingEnvironment): String =
            if (dataFetchingEnvironment.containsArgument("value")) {
                "The value was $value"
            } else {
                "The value was undefined"
            }
}
