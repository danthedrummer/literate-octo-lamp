package com.ddowney.customerselector

import com.ddowney.customerselector.models.Customer
import com.ddowney.customerselector.utils.DistanceCalculator
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Created by Dan on 20/01/2018.
 *
 * Unit tests for DistanceCalculator
 */
class DistanceCalculatorTests {

    private lateinit var distanceCalculator : DistanceCalculator

    @Before
    fun setUp() {
        distanceCalculator = DistanceCalculator()
    }

    @Test
    fun calculateDistanceFromOfficeShouldNotReturnNegativeValue() {
        //A and B are equidistant from the office in direct opposite directions
        val customerA = Customer("53.339407", " -7.257663", "Rick", 1)
        val distanceA = distanceCalculator.calculateDistanceFromOffice(customerA)

        val customerB = Customer("53.339407", " -5.257663", "Morty", 2)
        val distanceB = distanceCalculator.calculateDistanceFromOffice(customerB)

        assertTrue(distanceA.toDouble() > 0)
        assertTrue(distanceB.toDouble() > 0)
    }

    @Test(expected = IllegalStateException::class)
    fun calculateDistanceFromOfficeShouldThrowExceptionForCustomerWithMissingCoordinates() {
        val problemCustomer = Customer("", "", "Jerry", 1)
        distanceCalculator.calculateDistanceFromOffice(problemCustomer)
    }

    @Test
    fun calculateDistanceFromOfficeShouldNotReturnEmptyString() {
        val customer = Customer("54.15432", "-6.24465", "Beth", 1)
        val distance = distanceCalculator.calculateDistanceFromOffice(customer)

        assertTrue(distance.isNotEmpty())
    }

    @Test
    fun filterCustomersByDistanceShouldNotReturnAnyCustomersFurtherThanTheRange() {
        val customers = listOf(Customer("53.339407", " -7.257663", "Rick", 1),
                Customer("-53.339407", " 173.257663", "Morty", 2))

        val filteredCustomers = distanceCalculator.filterCustomersByDistance(customers, 100.0)

        filteredCustomers.forEach { customer ->
            if (distanceCalculator.calculateDistanceFromOffice(customer).toDouble() > 100) {
                fail("Customer outside of specified range")
            }
        }
    }
}