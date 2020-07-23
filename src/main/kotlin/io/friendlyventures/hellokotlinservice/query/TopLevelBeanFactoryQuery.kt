package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import kotlin.random.Random

/**
 * This is the top level query
 */
@Component
class TopLevelBeanFactoryQuery : Query, BeanFactoryAware {

    private lateinit var beanFactory: BeanFactory

    @GraphQLIgnore
    override fun setBeanFactory(beanFactory: BeanFactory) {
        this.beanFactory = beanFactory
    }

    fun topLevelBeanFactory(topLevelValue: String): SecondLevelBeanFactoryQuery = beanFactory.getBean(SecondLevelBeanFactoryQuery::class.java, topLevelValue)
}

/**
 * This is the second level object that is created at execution time of the
 * [TopLevelBeanFactoryQuery.topLevelBeanFactory] function.
 */
@Component
@Scope("prototype")
class SecondLevelBeanFactoryQuery
@Autowired(required = false)
constructor(internal val topLevelValue: String) : BeanFactoryAware {

    private lateinit var beanFactory: BeanFactory

    @GraphQLIgnore
    override fun setBeanFactory(beanFactory: BeanFactory) {
        this.beanFactory = beanFactory
    }

    fun secondLevel(secondLevelValue: String): ThirdLevelBeanFactoryQuery = beanFactory.getBean(ThirdLevelBeanFactoryQuery::class.java, topLevelValue, secondLevelValue)
}

/**
 * This is the third level object that is created at execution time of the
 * [SecondLevelBeanFactoryQuery.secondLevel] function.
 */
@Component
@Scope("prototype")
class ThirdLevelBeanFactoryQuery
@Autowired(required = false)
constructor(
    internal val topLevelValue: String,
    internal val secondLevelValue: String
) {

    @Autowired
    private lateinit var service: ExampleExternalService

    fun printMessage(thirdLevelValue: String): String =
        "topLevelValue=$topLevelValue, secondLevelValue=$secondLevelValue, thirdLevelValue=$thirdLevelValue, serviceValue=${service.getData()}"
}

/**
 * Spring service that represents some other component that is not
 * created from the bean factory
 */
@Service
class ExampleExternalService {
    fun getData(): Int = Random.nextInt()
}
