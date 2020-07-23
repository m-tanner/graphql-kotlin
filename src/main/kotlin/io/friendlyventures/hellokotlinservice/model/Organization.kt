package io.friendlyventures.hellokotlinservice.model

import com.expediagroup.graphql.annotations.GraphQLIgnore

data class Employee(
    val name: String,
    @GraphQLIgnore
    val companyId: Int
) {
    lateinit var company: Company
}

data class Company(val id: Int, val name: String)
