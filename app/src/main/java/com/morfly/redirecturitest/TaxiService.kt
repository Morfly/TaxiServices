package com.morfly.redirecturitest

data class Ride(val data: Any)

interface TaxiService {

    fun authorize()

    fun getRideInfo(): Single<Ride>
}