package pl.training.goodweather.common

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class CalculatorTest(private val a: Int, private val b: Int, private val result: Int) {

    companion object {

        @Parameters(name = "{index}: sum({0}, {1})")
        @JvmStatic
        fun data() = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(1, 3, 4)
        )

    }

    @Test
    fun should_add_numbers() {
        assertEquals(result, a + b)
    }


}