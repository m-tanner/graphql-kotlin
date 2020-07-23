package io.friendlyventures.hellokotlinservice

import com.expediagroup.graphql.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.spring.execution.ApolloSubscriptionHooks
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.execution.DataFetcherExceptionHandler
import io.friendlyventures.hellokotlinservice.directives.CustomDirectiveWiringFactory
import io.friendlyventures.hellokotlinservice.exceptions.CustomDataFetcherExceptionHandler
import io.friendlyventures.hellokotlinservice.execution.CustomDataFetcherFactoryProvider
import io.friendlyventures.hellokotlinservice.execution.MySubscriptionHooks
import io.friendlyventures.hellokotlinservice.execution.SpringDataFetcherFactory
import io.friendlyventures.hellokotlinservice.hooks.CustomSchemaGeneratorHooks
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class HelloKotlinServiceApplication {

    @Bean
    fun wiringFactory() = CustomDirectiveWiringFactory()

    @Bean
    fun hooks(wiringFactory: KotlinDirectiveWiringFactory) = CustomSchemaGeneratorHooks(wiringFactory)

    @Bean
    fun dataFetcherFactoryProvider(springDataFetcherFactory: SpringDataFetcherFactory, objectMapper: ObjectMapper) =
            CustomDataFetcherFactoryProvider(springDataFetcherFactory, objectMapper)

    @Bean
    fun dataFetcherExceptionHandler(): DataFetcherExceptionHandler = CustomDataFetcherExceptionHandler()

    @Bean
    fun apolloSubscriptionHooks(): ApolloSubscriptionHooks = MySubscriptionHooks()
}

fun main(args: Array<String>) {
    runApplication<HelloKotlinServiceApplication>(*args)
}
