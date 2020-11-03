package pl.training.goodweather.common

import com.google.common.collect.HashMultiset
import com.google.common.collect.ImmutableMultiset
import org.checkerframework.checker.units.qual.Time
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.junit.rules.ExpectedException
import org.junit.rules.Timeout
import org.junit.runners.MethodSorters
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestExamples {

    @Rule
    @JvmField
    val thrown: ExpectedException = ExpectedException.none()

    /* @Rule
    @JvmField
    val globalTimeout: Timeout = Timeout.seconds(5)*/

    companion object {

        @JvmStatic
        @BeforeClass
        fun beforeAll() {
            println("Before all")
        }

        @JvmStatic
        @AfterClass
        fun afterAll() {
            println("Before all")
        }

    }

    @Before
    fun beforeEach() {
        println("Before each")
    }

    @After
    fun afterEach() {
        println("After each")
    }

    @Ignore("not ready")
    @Test(timeout = 1000)
    fun testAssertEquals() {
        val text = "Kotlin"
        assertEquals("Kotlin", text)
    }

    @Test
    fun testAssertSame() {
        val text = "Kotlin"
        assertSame("Kotlin", text)
        TimeUnit.SECONDS.sleep(6)
    }

    @Test
    fun testThatArrayHasItems() {
        assertThat(listOf(1, 2, 3), hasItems(2, 3))
    }

    @Test
    fun testAssertThatBothStringsContainsText() {
        assertThat("Kotlin", both(containsString("K")).and(containsString("n")))
    }

    @Test
    fun testAssertThat() {
        //assertThat("Kotlin", not(allOf(startsWith("Ko"), equalTo("Kotlin"))))
        //assertThat("Kotlin", not(sameInstance("Kotlin")))
        assertThat("123", IsOnlyDigits())
    }

    @Test
    fun testTruthAssertions() {
        assertEquals(ImmutableMultiset.of("guava", "dagger", "truth", "auto", "caliper"), mapOf("guava" to "lib"))
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun assertException() {
        val number = listOf(2, 3)
        val result = number[10]
    }

    /*@Test
    fun assertExceptionWithLambda() {
        val exception = Assert.assertThrows {
            val number = listOf(2, 3)
            val result = number[10]
        }
        assertEquals(ArrayIndexOutOfBoundsException(), exception)
    }*/

    @Test
    fun assertExceptionWithFail() {
        val number = listOf(2, 3)
        //val result = number[10]
        fail()
    }

    @Test
    fun assertExceptionWithRule() {
        val number = listOf(2, 3)
        thrown.expect(ArrayIndexOutOfBoundsException::class.java)
        val result = number[10]
    }

    private val latch = CountDownLatch(1)

    @Test
    fun assertAsync() {
        val dataProvider = DataProvider()
        var result = ""
        dataProvider.get {
            result = it
            latch.countDown()
        }
        latch.await()
        assertEquals("success", result)
    }

    class DataProvider {

        fun get(callback: (String) -> Unit) {
            Thread {
                Timeout.seconds(3)
                println("After sleep")
                callback("success")
            }.start()
        }

    }

}