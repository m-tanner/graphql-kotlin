package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.friendlyventures.hellokotlinservice.model.Company
import io.friendlyventures.hellokotlinservice.model.Employee
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class CompanyService {
    private val companies = listOf(
        Company(id = 1, name = "FirstCompany"),
        Company(id = 2, name = "SecondCompany")
    )

    fun getCompanies(ids: List<Int>): List<Company> = companies
}

@Component
class EmployeeQuery : Query {
    private val employees = listOf(
        Employee(name = "Mike", companyId = 1),
        Employee(name = "John", companyId = 1),
        Employee(name = "Steve", companyId = 2)
    )

    @GraphQLDescription("Get all employees")
    fun employees(): List<Employee> {
        return employees
    }
}

@Component("CompanyDataFetcher")
@Scope("prototype")
class CompanyDataFetcher : DataFetcher<CompletableFuture<Company>>, BeanFactoryAware {
    private lateinit var beanFactory: BeanFactory

    override fun setBeanFactory(beanFactory: BeanFactory) {
        this.beanFactory = beanFactory
    }

    override fun get(environment: DataFetchingEnvironment): CompletableFuture<Company> {
        val companyId = environment.getSource<Employee>().companyId
        return environment
            .getDataLoader<Int, Company>("companyLoader")
            .load(companyId)
    }
}
