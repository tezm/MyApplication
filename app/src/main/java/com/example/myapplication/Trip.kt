package com.example.myapplication


class Trip(
    val trip_id : Int,
    val start_date : String,
    val end_date : String,
    val number_of_ppl : Int,
    val user : User?,
    val route : Route?,
    val boat : Boat?
) {
}