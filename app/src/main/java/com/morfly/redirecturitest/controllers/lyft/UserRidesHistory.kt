package com.morfly.redirecturitest.controllers.lyft

import com.uber.sdk.rides.client.model.Vehicle


/**
 * Created by Alex on 2/13/18.
 */
class UserRidesHistory {

    var rideHistory: List<RideHistory> = arrayListOf()

    inner class RideHistory {
        var ride_id: String? = null
        var status: String? = null
        var ride_type: String? = null
        var passenger: Passenger? = null
        var driver: Driver? = null
        var vehicle: Vehicle? = null
        var origin: Origin? = null
        var destination: Destination? = null
        var pickup: Pickup? = null
        var dropoff: Dropoff? = null
        var location: Location? = null
        var primetime_percentage: String? = null
        var price: Price? = null
        var line_items: List<LineItem>? = null
        var eta_seconds: Int? = null
        var requested_at: String? = null
    }

    inner class Passenger {
        var first_name: String? = null
        var phone_number: String? = null
    }

    inner class Driver {
        var first_name: String? = null
        var phone_number: String? = null
        var rating: String? = null
        var image_url: String? = null
    }

    inner class Origin {
        var lat: Float? = null
        var lng: Float? = null
        var address: String? = null
        var eta_seconds: Any? = null
    }

    inner class Destination {
        var lat: Float? = null
        var lng: Float? = null
        var address: String? = null
        var eta_seconds: Any? = null
    }

    inner class Pickup {
        var lat: Float? = null
        var lng: Float? = null
        var address: String? = null
        var time: String? = null
    }

    inner class Dropoff {
        var lat: Float? = null
        var lng: Float? = null
        var address: String? = null
        var time: String? = null
    }


    inner class Location {
        var lat: Float? = null
        var lng: Float? = null
        var address: String? = null
    }

    inner class Price {
        var amount: Int? = null
        var currency: String? = null
        var description: String? = null
    }

    inner class LineItem {
        var amount: Int? = null
        var currency: String? = null
        var type: String? = null
    }
}