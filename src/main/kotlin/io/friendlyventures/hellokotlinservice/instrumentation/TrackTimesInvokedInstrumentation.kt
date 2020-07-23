package io.friendlyventures.hellokotlinservice.instrumentation

import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.InstrumentationState
import graphql.execution.instrumentation.SimpleInstrumentation
import graphql.execution.instrumentation.SimpleInstrumentationContext
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters
import io.friendlyventures.hellokotlinservice.directives.TRACK_TIMES_INVOKED_DIRECTIVE_NAME
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

/**
 * Adds field count tracking in the instrumentation layer if [com.expediagroup.graphql.examples.directives.TrackTimesInvoked] is present.
 */
@Component
class TrackTimesInvokedInstrumentation : SimpleInstrumentation() {

    private val logger = LoggerFactory.getLogger(TrackTimesInvokedInstrumentation::class.java)

    override fun createState(): InstrumentationState = TrackTimesInvokedInstrumenationState()

    override fun beginFieldFetch(parameters: InstrumentationFieldFetchParameters): InstrumentationContext<Any> {
        if (parameters.field.getDirective(TRACK_TIMES_INVOKED_DIRECTIVE_NAME) != null) {
            (parameters.getInstrumentationState() as? TrackTimesInvokedInstrumenationState)?.incrementCount(parameters.field.name)
        }

        return SimpleInstrumentationContext<Any>()
    }

    override fun instrumentExecutionResult(executionResult: ExecutionResult, parameters: InstrumentationExecutionParameters): CompletableFuture<ExecutionResult> {
        val count = (parameters.getInstrumentationState() as? TrackTimesInvokedInstrumenationState)?.getCount()
        logger.info("TrackTimesInvokedInstrumentation fields invoked: $count")
        return super.instrumentExecutionResult(executionResult, parameters)
    }

    /**
     * The state per execution for this Instrumentation
     */
    private class TrackTimesInvokedInstrumenationState : InstrumentationState {

        private val fieldCount = ConcurrentHashMap<String, Int>()

        fun incrementCount(fieldName: String) {
            val currentCount = fieldCount.getOrDefault(fieldName, 0)
            fieldCount[fieldName] = currentCount.plus(1)
        }

        fun getCount() = fieldCount.toString()
    }
}
