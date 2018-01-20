package com.ddowney.customerselector.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddowney.customerselector.R
import com.ddowney.customerselector.models.Customer
import kotlinx.android.synthetic.main.customer_text_view.view.*

/**
 * Created by Dan on 20/01/2018.
 *
 * Recycler view adapter for Customer objects
 */
class CustomerListAdapter(private val data : List<Customer>, private val itemClick : (Customer) -> Unit)
    : RecyclerView.Adapter<CustomerListAdapter.ViewHolder>() {

    class ViewHolder(itemView : View, private val itemClick : (Customer) -> Unit)
        :RecyclerView.ViewHolder(itemView) {

        fun bindCustomer(customer : Customer) {
            with(customer) {
                itemView.customer_name.text = this.name
                itemView.customer_id.text = "Customer id: " + this.userId.toString()
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.customer_text_view, parent, false)
        return ViewHolder(v, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCustomer(data[position])
    }

    override fun getItemCount() = data.size
}