package com.ddowney.customerselector

import android.content.res.Resources
import com.ddowney.customerselector.models.Customer
import com.ddowney.customerselector.utils.JsonResourceReader
import com.google.gson.Gson
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import java.io.File
import org.junit.Assert.*
import java.io.IOException

/**
 * Created by Dan on 20/01/2018.
 *
 * Unit tests for JsonResourceReader
 */
@RunWith(MockitoJUnitRunner::class)
class JsonResourceReaderTests {

    private lateinit var resourceReader : JsonResourceReader
    private val gson = Gson()
    private val customer = Customer("10", "20", "Rick Sanchez", 15)
    private val file = File("customers.txt")

    @Mock
    private lateinit var mockResources : Resources

    @Before
    fun setUp() {
        if(file.exists()) {
            file.delete()
        }
        file.createNewFile()

        mockResources = Mockito.mock(Resources::class.java)
        Mockito.`when`(mockResources.openRawResource(0)).then {
            file.inputStream()
        }
    }

    @After
    fun tearDown() {
        val file = File("customers.txt")
        if(file.exists()) {
            file.delete()
        }
    }

    @Test
    fun constructUsingGsonShouldReturnAListOfEveryCustomerStoredInTheFile() {
        file.appendText(gson.toJson(customer))
        resourceReader = JsonResourceReader(mockResources, 0, gson)
        val list = resourceReader.constructUsingGson(Customer::class.java)
        assertTrue(list.size == 1)
        assertTrue(list[0].name == customer.name)
    }

    @Test
    fun constructUsingGsonShouldReturnAnEmptyListForAnEmptyFile() {
        resourceReader = JsonResourceReader(mockResources, 0, gson)
        val list = resourceReader.constructUsingGson(Customer::class.java)
        assertTrue(list.isEmpty())
    }

    @Test(expected = IOException::class)
    fun resourceReaderShouldThrowExceptionIfFileDoesNotExist() {
        file.delete()
        resourceReader = JsonResourceReader(mockResources, 0, gson)
    }
}