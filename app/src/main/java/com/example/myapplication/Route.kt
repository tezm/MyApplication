package com.example.myapplication

class Route(
    val route_id : Int,
    val length : Float,
    val duration : Int,
    val user : User?,
    val consistingPorts : ArrayList<Port>
) {
}