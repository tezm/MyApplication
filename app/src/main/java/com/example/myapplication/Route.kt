package com.example.myapplication

data class Route(
    var route_id : Int,
    var length : Float,
    var duration : Int,
    var user : User?,
    val consistingPorts : ArrayList<Port>
) {
}