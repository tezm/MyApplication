package com.example.myapplication

import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class DatabaseTests {
    private val db = DatabaseHelper(ApplicationProvider.getApplicationContext())

    @Test
    fun addUser_isCorrect() {
        val user = User(
            222, "John", "Doe", "2000-01-01", "M",
            "j.doe@gmail.com", "123456789", "password"
        )
        db.addUser(user)
        assertEquals(user, db.getUser(222))
        db.deleteUser(user)
    }

    @Test
    fun deleteUser_isCorrect() {
        val user = User(
            222, "John", "Doe", "2000-01-01", "M",
            "j.doe@gmail.com", "123456789", "password"
        )
        db.addUser(user)
        db.deleteUser(user)
        assertEquals(null, db.getUser(222))
    }

    @Test
    fun addPort_isCorrect() {
        val p1 = Port(
            222, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11
        )
        db.addPort(p1)
        assertEquals(p1, db.getPort(222))
        db.deletePort(p1)
    }

    @Test
    fun deletePort_isCorrect() {
        val p1 = Port(
            222, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11
        )
        db.addPort(p1)
        db.deletePort(p1)
        assertEquals(null, db.getPort(222))
    }

    @Test
    fun addBoat_isCorrect() {
        val p1 = Port(
            222, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11
        )
        val b1 = Boat(222, "super boat", "first boat ever", 130.99f, 20, 1, p1)
        db.addPort(p1)
        db.addBoat(b1)
        assertEquals(b1, db.getBoat(222))
        db.deleteBoat(b1)
        db.deletePort(p1)
    }

    @Test
    fun deleteBoat_isCorrect() {
        val p1 = Port(
            222, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11
        )
        val b1 = Boat(222, "super boat", "first boat ever", 130.99f, 20, 1, p1)
        db.addPort(p1)
        db.addBoat(b1)

        db.deleteBoat(b1)
        db.deletePort(p1)
        assertEquals(null, db.getBoat(222))
    }

    @Test
    fun addReachable_isCorrect() {
        val p1 = Port(
            111, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11
        )
        val p2 = Port(
            222, "port two", "this port is the second inserted port",
            2.22f, 2.22f, 76, 42
        )
        db.addPort(p1)
        db.addPort(p2)
        val r1 = IsReachable(p1, p2, 10.23f)
        db.addReachable(r1)
        val porst = db.getReachablePorts(p1)
        assertEquals(1, porst.size)
        assertEquals(p2, porst[0])
        db.deletePort(p1)
        db.deletePort(p2)
        db.deleteReachable(r1)
    }

    @Test
    fun deleteReachable_isCorrect() {
        val p1 = Port(
            111, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11
        )
        val p2 = Port(
            222, "port two", "this port is the second inserted port",
            2.22f, 2.22f, 76, 42
        )
        db.addPort(p1)
        db.addPort(p2)
        val r1 = IsReachable(p1, p2, 10.23f)
        db.addReachable(r1)
        db.deleteReachable(r1)
        val porst = db.getReachablePorts(p1)
        assertEquals(0, porst.size)
        db.deletePort(p1)
        db.deletePort(p2)

    }

    @Test
    fun addRoute_isCorrect() {
        val user = User(
            222, "John", "Doe", "2000-01-01", "M",
            "j.doe@gmail.com", "123456789", "password"
        )
        db.addUser(user)
        val p1 = Port(
            111, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11
        )
        val p2 = Port(
            222, "port two", "this port is the second inserted port",
            2.22f, 2.22f, 76, 42
        )
        val p3 = Port(
            333, "port three", "this port is the third inserted port",
            3.33f, 3.33f, 44, 34
        )
        db.addPort(p1)
        db.addPort(p2)
        db.addPort(p3)

        val arr = ArrayList<Port>()
        arr.add(p1)
        arr.add(p2)
        arr.add(p3)
        val route = Route(444, 5f, 3, user, arr)
        db.addRoute(route)
        assertEquals(route, db.getRoute(444))
        db.deleteRoute(route)
        db.deletePort(p1)
        db.deletePort(p2)
        db.deletePort(p3)
        db.deleteUser(user)
    }

    @Test
    fun deleteRoute_isCorrect() {
        val user = User(
            222, "John", "Doe", "2000-01-01", "M",
            "j.doe@gmail.com", "123456789", "password"
        )
        db.addUser(user)
        val p1 = Port(
            111, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11
        )
        val p2 = Port(
            222, "port two", "this port is the second inserted port",
            2.22f, 2.22f, 76, 42
        )
        val p3 = Port(
            333, "port three", "this port is the third inserted port",
            3.33f, 3.33f, 44, 34
        )
        db.addPort(p1)
        db.addPort(p2)
        db.addPort(p3)

        val arr = ArrayList<Port>()
        arr.add(p1)
        arr.add(p2)
        arr.add(p3)
        val route = Route(444, 5f, 3, user, arr)
        db.addRoute(route)

        db.deleteRoute(route)
        assertEquals(null, db.getRoute(444))
        db.deletePort(p1)
        db.deletePort(p2)
        db.deletePort(p3)
        db.deleteUser(user)
    }

    @Test
    fun addTrip_isCorrect() {
        val user = User(
            222, "John", "Doe", "2000-01-01", "M",
            "j.doe@gmail.com", "123456789", "password"
        )
        db.addUser(user)
        val p1 = Port(
            111, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11
        )
        val p2 = Port(
            222, "port two", "this port is the second inserted port",
            2.22f, 2.22f, 76, 42
        )
        val p3 = Port(
            333, "port three", "this port is the third inserted port",
            3.33f, 3.33f, 44, 34
        )
        db.addPort(p1)
        db.addPort(p2)
        db.addPort(p3)
        val b1 = Boat(222, "super boat", "first boat ever", 130.99f, 20, 1, p1)
        db.addBoat(b1)
        val arr = ArrayList<Port>()
        arr.add(p1)
        arr.add(p2)
        arr.add(p3)
        val route = Route(444, 5f, 3, user, arr)
        db.addRoute(route)
        val trip = Trip(666, "2023-01-26", "2023-01-31", 6, user, route, b1)
        db.addTrip(trip)
        assertEquals(trip, db.getTrip(666))
        db.deleteTrip(trip)
        db.deleteRoute(route)

        db.deleteBoat(b1)
        db.deletePort(p1)
        db.deletePort(p2)
        db.deletePort(p3)
        db.deleteUser(user)
    }

    @Test
    fun deleteTrip_isCorrect() {
        val user = User(
            222, "John", "Doe", "2000-01-01", "M",
            "j.doe@gmail.com", "123456789", "password"
        )
        db.addUser(user)
        val p1 = Port(
            111, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11
        )
        val p2 = Port(
            222, "port two", "this port is the second inserted port",
            2.22f, 2.22f, 76, 42
        )
        val p3 = Port(
            333, "port three", "this port is the third inserted port",
            3.33f, 3.33f, 44, 34
        )
        db.addPort(p1)
        db.addPort(p2)
        db.addPort(p3)
        val b1 = Boat(222, "super boat", "first boat ever", 130.99f, 20, 1, p1)
        db.addBoat(b1)
        val arr = ArrayList<Port>()
        arr.add(p1)
        arr.add(p2)
        arr.add(p3)
        val route = Route(444, 5f, 3, user, arr)
        db.addRoute(route)
        val trip = Trip(666, "2023-01-26", "2023-01-31", 6, user, route, b1)
        db.addTrip(trip)

        db.deleteTrip(trip)
        assertEquals(null, db.getTrip(666))
        db.deleteRoute(route)

        db.deleteBoat(b1)
        db.deletePort(p1)
        db.deletePort(p2)
        db.deletePort(p3)
        db.deleteUser(user)
    }
}