package io.friendlyventures.hellokotlinservice.metrics

import com.codahale.metrics.MetricRegistry
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.dropwizard.DropwizardExports
import io.prometheus.client.exporter.MetricsServlet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary

@Configuration
@EnableMetrics
class MetricsConfiguration : MetricsConfigurerAdapter() {
    @Autowired
    private val collectorRegistry: CollectorRegistry? = null

    @Bean
    fun collectorRegistry(): CollectorRegistry {
        return CollectorRegistry()
    }

    @Bean
    @DependsOn("collectorRegistry")
    fun metricsServletRegistrationBean(): ServletRegistrationBean<MetricsServlet> {
        return ServletRegistrationBean(MetricsServlet(collectorRegistry()),
                "/prometheusMetrics")
    }

    override fun configureReporters(metricRegistry: MetricRegistry) {
        collectorRegistry!!.register(DropwizardExports(metricRegistry))
    }

    @Bean(name = ["portalMetricFilter"])
    fun metricsFilter(): MetricsFilter {
        return MetricsFilter()
    }

    @Primary
    @Bean(name = ["portalMetricRegistry"])
    fun metricRegistry(): MetricRegistry {
        val registry = MetricRegistry()
        configureReporters(registry)
        return registry
    }
}
