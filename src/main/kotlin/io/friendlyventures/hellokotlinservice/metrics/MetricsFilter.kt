package io.friendlyventures.hellokotlinservice.metrics

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.jvm.BufferPoolMetricSet
import com.codahale.metrics.jvm.GarbageCollectorMetricSet
import com.codahale.metrics.jvm.MemoryUsageGaugeSet
import com.codahale.metrics.jvm.ThreadStatesGaugeSet
import org.springframework.beans.factory.annotation.Autowired
import java.io.IOException
import java.lang.management.ManagementFactory
import javax.servlet.*
import javax.servlet.http.HttpServletResponse

class MetricsFilter : Filter {
    @Autowired
    private val metricsRegistry: MetricRegistry? = null

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        metricsRegistry!!.register("jvm.buffers",
                BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()))
        metricsRegistry.register("jvm.gc", GarbageCollectorMetricSet())
        metricsRegistry.register("jvm.memory", MemoryUsageGaugeSet())
        metricsRegistry.register("jvm.threads", ThreadStatesGaugeSet())
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        chain.doFilter(request, response)
        val status = (response as HttpServletResponse).status
        metricsRegistry!!.counter("response_status_$status").inc()
    }

    override fun destroy() {}
}
