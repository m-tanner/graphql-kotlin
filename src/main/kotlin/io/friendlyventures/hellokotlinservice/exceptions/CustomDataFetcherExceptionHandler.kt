package io.friendlyventures.hellokotlinservice.exceptions

import com.expediagroup.graphql.spring.exception.SimpleKotlinGraphQLError
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import graphql.ErrorType
import graphql.ErrorType.ValidationError
import graphql.ExceptionWhileDataFetching
import graphql.GraphQLError
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import graphql.execution.ExecutionPath
import graphql.language.SourceLocation
import org.slf4j.LoggerFactory

class CustomDataFetcherExceptionHandler : DataFetcherExceptionHandler {
    private val log = LoggerFactory.getLogger(CustomDataFetcherExceptionHandler::class.java)

    override fun onException(handlerParameters: DataFetcherExceptionHandlerParameters): DataFetcherExceptionHandlerResult {
        val exception = handlerParameters.exception
        val sourceLocation = handlerParameters.sourceLocation
        val path = handlerParameters.path

        val error: GraphQLError = when (exception) {
            is ValidationException -> ValidationDataFetchingGraphQLError(exception.constraintErrors, path, exception, sourceLocation)
            else -> SimpleKotlinGraphQLError(exception = exception, locations = listOf(sourceLocation), path = path.toList())
        }
        log.warn(error.message, exception)
        return DataFetcherExceptionHandlerResult.newResult().error(error).build()
    }
}

@JsonIgnoreProperties("exception")
class ValidationDataFetchingGraphQLError(
        val constraintErrors: List<ConstraintError>,
        path: ExecutionPath,
        exception: Throwable,
        sourceLocation: SourceLocation
) : ExceptionWhileDataFetching(
    path,
    exception,
    sourceLocation
) {
    override fun getErrorType(): ErrorType = ValidationError
}
