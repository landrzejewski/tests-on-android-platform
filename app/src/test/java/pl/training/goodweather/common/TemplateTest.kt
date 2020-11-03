package pl.training.goodweather.common

import org.junit.Test

import org.junit.Assert.*
import java.lang.IllegalArgumentException

class TemplateTest {

    private val template = Template("My name is #{firstName} #{lastName}")

    @Test
    fun should_evaluate_text_with_expressions() {
        val parameters = mapOf("firstName" to "Jan", "lastName" to "Kowalski")
        assertEquals("My name is Jan Kowalski", template.evaluate(parameters))
    }

    @Test(expected = IllegalArgumentException::class)
    fun should_throw_exception_when_parameter_is_missing() {
        template.evaluate(emptyMap())
    }

    @Test(expected = IllegalArgumentException::class)
    fun should_accept_only_alphanumeric_parameters() {
        val parameters = mapOf("firstName" to "**", "lastName" to "**")
        template.evaluate(parameters)
    }

}