@file:Suppress("unused")

package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.spring.operations.Query
import io.friendlyventures.hellokotlinservice.directives.TrackTimesInvoked
import io.netty.util.internal.ThreadLocalRandom
import org.springframework.stereotype.Component
import java.util.*

/**
 * Used to stress test the performance of running many data fetchers.
 * Tests properties vs functions vs suspend functions.
 */
@Component
class StressQuery : Query {

    fun stressNode(traceId: String?, count: Int?): List<StressNode> {
        val id = traceId ?: getRandomStringFromThread()
        return (1..(count ?: 1)).map { StressNode(id) }
    }
}

@Suppress("MemberVisibilityCanBePrivate", "RedundantSuspendModifier")
class StressNode(val traceId: String) {

    val valueId: String = getRandomStringFromThread()

    fun functionId(): String = getRandomStringFromThread()

    suspend fun suspendId(): String = getRandomStringFromThread()

    @TrackTimesInvoked
    fun loggingFunctionId(): String = getRandomStringFromThread()

    @TrackTimesInvoked
    suspend fun suspendLoggingFunctionId(): String = getRandomStringFromThread()
}

private fun getRandomStringFromThread(): String {
    val random = ThreadLocalRandom.current()
    return UUID(random.nextLong(), random.nextLong()).toString()
}
