package io.friendlyventures.hellokotlinservice.directives

import com.expediagroup.graphql.directives.KotlinFieldDirectiveEnvironment
import com.expediagroup.graphql.directives.KotlinSchemaDirectiveWiring
import graphql.schema.DataFetcher
import graphql.schema.GraphQLFieldDefinition

class SpecificValueOnlySchemaDirectiveWiring : KotlinSchemaDirectiveWiring {

    @Throws(RuntimeException::class)
    override fun onField(environment: KotlinFieldDirectiveEnvironment): GraphQLFieldDefinition {
        val field = environment.element
        val originalDataFetcher: DataFetcher<*> = environment.getDataFetcher()

        val supportedValue = environment.directive.getArgument("value")?.value?.toString() ?: ""

        val cakeOnlyFetcher = DataFetcher<Any> { dataEnv ->
            val strArg: String? = dataEnv.getArgument(environment.element.arguments[0].name) as String?
            if (!supportedValue.equals(other = strArg, ignoreCase = true)) {
                throw RuntimeException("Unsupported value, expected=$supportedValue actual=$strArg")
            }
            originalDataFetcher.get(dataEnv)
        }
        environment.setDataFetcher(cakeOnlyFetcher)
        return field
    }
}
