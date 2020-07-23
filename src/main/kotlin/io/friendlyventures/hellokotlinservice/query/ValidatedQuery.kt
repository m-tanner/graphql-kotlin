package io.friendlyventures.hellokotlinservice.query

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.Valid
import javax.validation.constraints.Pattern

@Validated
@Component
class ValidatedQuery : Query {
    fun argumentWithValidation(@Valid arg: TypeWithPattern): String = arg.lowerCaseOnly
}

data class TypeWithPattern(
        @field:Pattern(regexp = "^[a-z]+$", message = "Argument must be lowercase")
        val lowerCaseOnly: String
)
