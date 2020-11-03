package pl.training.goodweather.common

import org.junit.Test
import org.junit.experimental.categories.Categories
import org.junit.experimental.categories.Categories.ExcludeCategory
import org.junit.experimental.categories.Categories.IncludeCategory
import org.junit.experimental.categories.Category
import org.junit.experimental.categories.CategoryValidator
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

interface Slow
interface Fast

class TestCategories {

    @Test
    fun testA() {
        println("A")
    }

    @Category(Slow::class)
    @Test
    fun testB() {
        println("B")
    }

    @Category(Fast::class)
    @Test
    fun testC() {
        println("B")
    }

}

@RunWith(Categories::class)
@IncludeCategory(Slow::class)
@ExcludeCategory(Fast::class)
@SuiteClasses(TestCategories::class)
class SlowSuit