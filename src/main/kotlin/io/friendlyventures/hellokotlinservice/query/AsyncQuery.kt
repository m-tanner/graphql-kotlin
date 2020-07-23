package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.time.delay
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.concurrent.CompletableFuture

/**
 * Example async queries.
 */
@Component
class AsyncQuery : Query {

    @GraphQLDescription(
        "Delays for given amount of time using CompletableFuture and then echoes the string back." +
            " The default async executor will work with CompletableFuture." +
            " To use other rx frameworks you'll need to install a custom one to handle the types correctly."
    )
    fun delayedEchoUsingCompletableFuture(msg: String, delayMilliseconds: Int): CompletableFuture<String> {
        val future = CompletableFuture<String>()
        Thread {
            Thread.sleep(delayMilliseconds.toLong())
            future.complete(msg)
        }.start()
        return future
    }

    @GraphQLDescription("Delays for given amount of time using Reactor Mono and then echoes the string back.")
    fun delayedEchoUsingReactorMono(msg: String, delayMilliseconds: Int): Mono<String> =
        Mono.just(msg).delayElement(Duration.ofMillis(delayMilliseconds.toLong()))

    @GraphQLDescription("Delays for given amount of time using Coroutine and then echoes the string back.")
    suspend fun delayedEchoUsingCoroutine(msg: String, delayMilliseconds: Int): String = coroutineScope {
        delay(Duration.ofMillis(delayMilliseconds.toLong()))
        msg
    }
}
