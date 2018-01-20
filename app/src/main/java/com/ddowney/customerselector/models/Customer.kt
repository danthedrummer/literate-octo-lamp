package com.ddowney.customerselector.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Dan on 20/01/2018.
 *
 * Describes a customer
 */
data class Customer(val latitude : String,
                    val longitude : String,
                    val name : String,
                    @SerializedName("user_id") val userId : Int)