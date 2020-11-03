package pl.training.goodweather.common

import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertThat
import org.junit.Assume
import org.junit.Assume.assumeThat
import org.junit.experimental.theories.DataPoint
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class TheoriesTest {

    companion object {

        @DataPoint
        const val fileName = "weather.kt"

        @DataPoint
        const val path = "/files/weather"

    }

    @Theory
    fun fileNameContainsExtension(fileName : String) {
        assumeThat(fileName, not(containsString("/")))
        assertThat(fileName, containsString(".kt"))
    }

}