package com.morfly.redirecturitest

import android.content.Intent

data class Ride(val data: Any)

interface TaxiService {

    fun authorize()

    fun getRideInfo()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}