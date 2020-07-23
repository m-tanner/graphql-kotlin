package io.friendlyventures.hellokotlinservice.model

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.annotations.GraphQLName
import com.fasterxml.jackson.annotation.JsonProperty

@GraphQLDescription("Use to represent a selection when choosing a value")
enum class Selection {

    @GraphQLDescription("Use this when you want the first one")
    ONE,

    // If we change the name, we need to update Jackson as well
    @JsonProperty("second")
    @GraphQLName("second")
    @GraphQLDescription("Use this when you want the second one")
    TWO
}
