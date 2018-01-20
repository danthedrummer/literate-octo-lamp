package com.ddowney.customerselector

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.ddowney.customerselector.adapters.CustomerListAdapter
import com.ddowney.customerselector.models.Customer
import com.ddowney.customerselector.utils.DistanceCalculator
import com.ddowney.customerselector.utils.JsonResourceReader
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var fullCustomerList: List<Customer> = listOf()
    private val distanceCalculator = DistanceCalculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        fullCustomerList = JsonResourceReader(resources, R.raw.customers, Gson())
                .constructUsingGson(Customer::class.java)

        val filteredCustomers = distanceCalculator.filterCustomersByDistance(fullCustomerList, 100.0)
                .sortedBy { customer -> customer.userId }

        val customerListAdapter = CustomerListAdapter(filteredCustomers) { customer ->
            val message = customer.name + " is " +
                    distanceCalculator.calculateDistanceFromOffice(customer) + "km from the office"
            val snack = Snackbar.make(main_activity_layout, message, Snackbar.LENGTH_LONG)
            snack.show()
        }

        customer_recycler_view.setHasFixedSize(true)
        customer_recycler_view.layoutManager = LinearLayoutManager(this)
        customer_recycler_view.adapter = customerListAdapter

        Snackbar.make(main_activity_layout,
                "Showing customers within 100km", Snackbar.LENGTH_LONG).show()
    }
}
