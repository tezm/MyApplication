package com.example.myapplication

data class Boat(
    val boat_id : Int,
    val name : String,
    val description : String,
    val price : Float,
    val capacity : Int,
    val availability : Int,
    val port : Port?
) {
}