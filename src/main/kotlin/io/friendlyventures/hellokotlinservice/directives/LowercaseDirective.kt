package io.friendlyventures.hellokotlinservice.directives

import com.expediagroup.graphql.annotations.GraphQLDirective

@GraphQLDirective(name = "lowercase", description = "Modifies the string field to lowercase")
annotation class LowercaseDirective
