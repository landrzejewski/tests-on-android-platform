package pl.training.goodweather.common

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assume
import org.junit.Assume.assumeThat
import org.junit.Test
import java.io.File

class AssumptionsTest {

    @Test
    fun test() {
        assumeThat(File.separatorChar, CoreMatchers.`is`("/"))
        assertFalse(true)
    }


}