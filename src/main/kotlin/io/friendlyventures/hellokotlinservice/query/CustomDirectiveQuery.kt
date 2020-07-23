package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import io.friendlyventures.hellokotlinservice.directives.LowercaseDirective
import io.friendlyventures.hellokotlinservice.directives.SpecificValueOnly
import io.friendlyventures.hellokotlinservice.directives.StringEval
import org.springframework.stereotype.Component

@Component
class CustomDirectiveQuery : Query {

    @GraphQLDescription("Returns a message modified by directives, lower case and non-empty")
    fun justWhisper(@StringEval(default = "default string", lowerCase = true) msg: String?): String? = msg

    @GraphQLDescription("This will only accept 'Cake' as input")
    @SpecificValueOnly("cake")
    fun onlyCake(msg: String): String = "<3"

    @GraphQLDescription("This will only accept 'IceCream' as input")
    @SpecificValueOnly("icecream")
    fun onlyIceCream(msg: String): String = "<3"

    @GraphQLDescription("Returns message modified by the manually wired directive to force lowercase")
    @LowercaseDirective
    fun forceLowercaseEcho(msg: String) = msg
}
