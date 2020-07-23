package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.spring.exception.SimpleKotlinGraphQLError
import com.expediagroup.graphql.spring.operations.Query
import graphql.execution.DataFetcherResult
import graphql.execution.ExecutionPath
import graphql.language.SourceLocation
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class DataAndErrorsQuery : Query {

    fun returnDataAndErrors(): DataFetcherResult<String?> {
        val error = SimpleKotlinGraphQLError(RuntimeException("data and errors"), listOf(SourceLocation(1, 1)), ExecutionPath.rootPath().toList())
        return DataFetcherResult.newResult<String>()
            .data("Hello from data fetcher")
            .error(error)
            .build()
    }

    fun completableFutureDataAndErrors(): CompletableFuture<DataFetcherResult<String?>> {
        val error = SimpleKotlinGraphQLError(RuntimeException("data and errors"), listOf(SourceLocation(1, 1)), ExecutionPath.rootPath().toList())
        val dataFetcherResult = DataFetcherResult.newResult<String>()
            .data("Hello from data fetcher")
            .error(error)
            .build()
        return CompletableFuture.completedFuture(dataFetcherResult)
    }
}
