package pl.training.goodweather.common

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.IllegalStateException

//@RunWith(MockitoJUnitRunner::class)
class MockitoExamples {

    @Mock
    lateinit var mockedList: MutableList<Int>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(MockitoExamples::class)
    }

    @Test
    fun verifyTest() {
        //val mockedList = mock<MutableList<Int>>()
        //`when`(mockedList[1]).thenReturn(1)
        `when`(mockedList[anyInt()]).thenReturn(1)
        doThrow(IllegalStateException::class.java).`when`(mockedList).clear()

        mockedList.add(1)

        verify(mockedList).add(1)
        verify(mockedList)[anyInt()]
        verify(mockedList, times(2))[anyInt()]
        verify(mockedList, atMost(5))[anyInt()]
        verifyNoInteractions(mockedList)
        assertEquals(1, mockedList[1])
    }

}