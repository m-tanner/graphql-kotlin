package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import io.friendlyventures.hellokotlinservice.model.Widget
import org.springframework.stereotype.Component

/**
 * Simple widget query.
 */
@Component
class WidgetQuery : Query {

    @GraphQLDescription("creates new widget for given ID")
    fun widgetById(@GraphQLDescription("The special ingredient") id: Int): Widget? = Widget(id)
}
