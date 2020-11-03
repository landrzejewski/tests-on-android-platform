package pl.training.goodweather.common

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import java.lang.NumberFormatException

class IsOnlyDigits : TypeSafeMatcher<String>() {

    override fun describeTo(description: Description) {
        description.appendText("only digits")
    }

    override fun matchesSafely(item: String): Boolean {
        return try {
            item.toInt()
            true
        } catch (e : NumberFormatException) {
            false
        }
    }

}