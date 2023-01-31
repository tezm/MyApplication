package com.example.myapplication


data class Trip(
    var trip_id : Int,
    var start_date : String,
    var end_date : String,
    var number_of_ppl : Int,
    var user : User?,
    var route : Route?,
    var boat : Boat?
) {
}