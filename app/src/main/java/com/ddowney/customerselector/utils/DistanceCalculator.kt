package com.ddowney.customerselector.utils

import com.ddowney.customerselector.models.Customer

/**
 * Created by Dan on 20/01/2018.
 *
 * Class that provides methods for calculating a customer's distance from the office
 */
class DistanceCalculator {

    companion object {
        private val earthRadius = 6371
        private val officeLatitudeRadians = 0.930948639728
        private val officeLongitudeRadians = -0.10921684028
    }

    /**
     * Calculates a customer's distance from the Intercom office in
     * kilometers rounded to 2 decimal places.
     *
     * @param customer The customer to be examined
     *
     * @return The customer's distance from the office as a String
     */
    fun calculateDistanceFromOffice(customer: Customer) : String {

        //Despite the non-nullable types, these values can still be null after parsing from json
        if (customer.latitude.isNullOrEmpty() || customer.longitude.isNullOrEmpty()) {
            throw IllegalStateException("Missing coordinates for " + customer.name)
        }

        val customerLatitudeRadians = Math.toRadians(customer.latitude.toDouble())
        val customerLongitudeRadians = Math.toRadians(customer.longitude.toDouble())

        val x = Math.sin(officeLatitudeRadians) * Math.sin(customerLatitudeRadians)
        val y = Math.cos(officeLatitudeRadians) * Math.cos(customerLatitudeRadians) *
                Math.cos(Math.abs(officeLongitudeRadians - customerLongitudeRadians))
        val centralAngle = Math.acos(x + y)

        return String.format("%.2f", earthRadius * centralAngle)
    }

    /**
     * Filters a list of Customer objects based on their proximity to the Intercom office
     *
     * @param customers The list of customers to be filtered
     * @param range The range (in km) to be used as a filter
     *
     * @return The filtered list of customers
     */
    fun filterCustomersByDistance(customers : List<Customer>, range: Double) : List<Customer> {
        return customers.filter { customer -> calculateDistanceFromOffice(customer).toDouble() < range }
    }
}