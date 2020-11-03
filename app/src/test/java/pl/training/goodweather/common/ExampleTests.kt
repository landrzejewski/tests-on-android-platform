package pl.training.goodweather.common

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ExampleTests {

    @BeforeEach
    fun setup() {
        println("before each")
    }

   /* @Test
    fun `When button is clicked then launch activity with forecast details`() {
        println("test")
    }

    @Test
    fun `When button is clicked then state should be saved`() {
        println("test")
    }*/

    @Nested
    @DisplayName("When button is clicked")
    inner class WhenButtonClicked {

        @DisplayName("then launch activity with forecast details")
        @Test
        fun launchActivity() {
            println("test")
        }

        @DisplayName("then state should be saved")
        @Test
        fun saveState() {
            println("test")
        }

    }

}