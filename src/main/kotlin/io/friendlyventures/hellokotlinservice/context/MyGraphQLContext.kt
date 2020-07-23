package io.friendlyventures.hellokotlinservice.context

import com.expediagroup.graphql.execution.GraphQLContext
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse

/**
 * Simple [GraphQLContext] that holds extra value.
 */
class MyGraphQLContext(val myCustomValue: String, val request: ServerHttpRequest, val response: ServerHttpResponse, var subscriptionValue: String? = null) : GraphQLContext
