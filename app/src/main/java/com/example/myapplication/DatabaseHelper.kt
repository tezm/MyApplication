package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class DatabaseHelper(val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "SailingDatabase.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val create_ports = """CREATE TABLE PORTS(
            port_id INTEGER PRIMARY KEY,
            name TEXT,
            description TEXT,
            x_coordinate REAL,
            y_coordinate REAL,
            max_capacity INTEGER,
            boat_capacity INTEGER
            )"""
        db?.execSQL(create_ports)

        val create_boats = """CREATE TABLE BOATS (
                boat_id INTEGER PRIMARY KEY,
                name TEXT,
                description TEXT,
                price REAL,
                capacity INTEGER,
                availability INTEGER,
                port_id INTEGER,
                FOREIGN KEY (port_id) REFERENCES PORTS(port_id) ON DELETE CASCADE
        )"""
        db?.execSQL(create_boats)

        val create_users = """CREATE TABLE USERS (
            user_id INTEGER PRIMARY KEY,
            name TEXT,
            surname TEXT,
            birthdate TEXT,
            gender TEXT,
            email TEXT,
            phone TEXT,
            password TEXT
            )"""
        db?.execSQL(create_users)

        val create_routes = """CREATE TABLE ROUTES (
            route_id INTEGER PRIMARY KEY,
            length REAL,
            duration INTEGER,
            user_id INTEGER,
            FOREIGN KEY (user_id) REFERENCES USERS(user_id) ON DELETE SET NULL
            )"""
        db?.execSQL(create_routes)

        val create_trips = """CREATE TABLE TRIPS (
            trip_id INTEGER PRIMARY KEY,
            start_date TEXT,
            end_date TEXT,
            number_of_ppl INTEGER,
            user_id INTEGER,
            route_id INTEGER,
            boat_id INTEGER,
            FOREIGN KEY (user_id) REFERENCES USERS(user_id) ON DELETE CASCADE,
            FOREIGN KEY (route_id) REFERENCES ROUTES(route_id) ON DELETE SET NULL,
            FOREIGN KEY (boat_id) REFERENCES BOATS(boat_id) ON DELETE SET NULL
        )"""
        db?.execSQL(create_trips)

        val create_reachable = """CREATE TABLE IS_REACHABLE_FROM (
            port_id_1 INTEGER,
            port_id_2 INTEGER,
            distance REAL,
            PRIMARY KEY (port_id_1, port_id_2),
            FOREIGN KEY (port_id_1) REFERENCES PORTS(port_id_1) ON DELETE CASCADE,
            FOREIGN KEY (port_id_2) REFERENCES PORTS(port_id_2) ON DELETE CASCADE
            )"""
        db?.execSQL(create_reachable)

        val create_consists = """CREATE TABLE CONSISTS_OF (
            route_id INTEGER,
            port_id INTEGER,
            PRIMARY KEY (route_id, port_id),
            FOREIGN KEY (port_id) REFERENCES PORTS(port_id) ON DELETE CASCADE,
            FOREIGN KEY (route_id) REFERENCES ROUTES(route_id) ON DELETE CASCADE
            )"""
        db?.execSQL(create_consists)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS CONSISTS_OF")
        db?.execSQL("DROP TABLE IF EXISTS IS_REACHABLE_FROM")
        db?.execSQL("DROP TABLE IF EXISTS TRIPS")
        db?.execSQL("DROP TABLE IF EXISTS ROUTES")
        db?.execSQL("DROP TABLE IF EXISTS USERS")
        db?.execSQL("DROP TABLE IF EXISTS BOATS")
        db?.execSQL("DROP TABLE IF EXISTS PORTS")
        onCreate(db)
    }

    fun clear() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM USERS")
        db.execSQL("DELETE FROM PORTS")
        db.execSQL("DELETE FROM IS_REACHABLE_FROM")
        db.execSQL("DELETE FROM BOATS")
        db.execSQL("DELETE FROM ROUTES")
        db.execSQL("DELETE FROM TRIPS")
        db.execSQL("DELETE FROM CONSISTS_OF")
    }

    fun populate() {
        val user = User(1, "John", "Doe", "2000-01-01", "M",
                        "j.doe@gmail.com", "123456789", "password")
        addUser(user)
        val p1 = Port(1, "port one", "this port is the first inserted port",
            1.11f, 1.11f, 47, 11)
        val p2 = Port(2, "port two", "this port is the second inserted port",
            2.22f, 2.22f, 76, 42)
        val p3 = Port(3, "port three", "this port is the third inserted port",
            3.33f, 3.33f, 44, 34)
        val p4 = Port(4, "port four", "this port is the fourth inserted port",
            4.44f, 4.44f, 12, 8)
        val p5 = Port(5, "port five", "this port is the fifth inserted port",
            5.55f, 5.55f, 32, 22)
        addPort(p1)
        addPort(p2)
        addPort(p3)
        addPort(p4)
        addPort(p5)
        val b1 = Boat(1, "super boat", "first boat ever", 130.99f, 20, 1, p1)
        val b2 = Boat(2, "mega boat", "second boat ever", 123.99f, 15, 1, p2)
        val b3 = Boat(3, "extra boat", "third boat ever", 111.99f, 16, 1, p3)
        val b4 = Boat(4, "hiper boat", "fourth boat ever", 100.99f, 10, 1, p1)
        val b5 = Boat(5, "giga boat", "fifth boat ever", 90.99f, 6, 1, p4)
        val b6 = Boat(6, "turbo boat", "sixth boat ever", 127.99f, 18, 1, p3)
        val b7 = Boat(7, "big boat", "seventh boat ever", 132.99f, 24, 1, p4)
        val b8 = Boat(8, "small boat", "eighth boat ever", 60.99f, 4, 1, p2)
        val b9 = Boat(9, "medium boat", "ninth boat ever", 137.99f, 15, 0, p5)
        val b10 = Boat(10, "green boat", "tenth boat ever", 128.99f, 18, 1, p2)
        addBoat(b1)
        addBoat(b2)
        addBoat(b3)
        addBoat(b4)
        addBoat(b5)
        addBoat(b6)
        addBoat(b7)
        addBoat(b8)
        addBoat(b9)
        addBoat(b10)
        val r1 = IsReachable(p1, p2, 10.23f)
        val r2 = IsReachable(p2, p1, 10.23f)
        val r3 = IsReachable(p1, p3, 5.11f)
        val r4 = IsReachable(p3, p1, 5.11f)
        val r5 = IsReachable(p1, p4, 6.98f)
        val r6 = IsReachable(p4, p1, 6.98f)
        val r7 = IsReachable(p2, p3, 4.11f)
        val r8 = IsReachable(p3, p2, 4.11f)
        val r9 = IsReachable(p3, p4, 4.43f)
        val r10 = IsReachable(p4, p3, 4.43f)
        val r11 = IsReachable(p3, p5, 7.23f)
        val r12 = IsReachable(p5, p3, 7.23f)
        val r13 = IsReachable(p4, p5, 3.33f)
        val r14 = IsReachable(p5, p4, 3.33f)
        addReachable(r1)
        addReachable(r2)
        addReachable(r3)
        addReachable(r4)
        addReachable(r5)
        addReachable(r6)
        addReachable(r7)
        addReachable(r8)
        addReachable(r9)
        addReachable(r10)
        addReachable(r11)
        addReachable(r12)
        addReachable(r13)
        addReachable(r14)
        val arr = ArrayList<Port>()
        arr.add(p1)
        arr.add(p2)
        arr.add(p3)
        val length = r1.distance + r7.distance
        val route = Route(1, length, 3, user, arr)
        addRoute(route)
        val trip = Trip(1, "2023-01-26", "2023-01-31", 6, user, route, b1)
        addTrip(trip)
    }


    // the following functions return -1 if the operation is failed, else index in the table

    fun addPort(port: Port): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("port_id", port.port_id)
        contentValues.put("name", port.name)
        contentValues.put("description", port.description)
        contentValues.put("x_coordinate", port.x_coordinate)
        contentValues.put("y_coordinate", port.y_coordinate)
        contentValues.put("max_capacity", port.max_capacity)
        contentValues.put("boat_capacity", port.boat_capacity)
        val result = db.insert("PORTS", null, contentValues)
        db.close()
        return result
    }

    fun updatePort(port: Port): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("port_id", port.port_id)
        contentValues.put("name", port.name)
        contentValues.put("description", port.description)
        contentValues.put("x_coordinate", port.x_coordinate)
        contentValues.put("y_coordinate", port.y_coordinate)
        contentValues.put("max_capacity", port.max_capacity)
        contentValues.put("boat_capacity", port.boat_capacity)
        val where_clause = "port_id = ${port.port_id}"
        val result = db.update("PORTS", contentValues, where_clause, null)
        db.close()
        return result
    }

    fun deletePort(port: Port): Int {
        val db = this.writableDatabase
        val where_clause = "port_id = ${port.port_id}"
        val result = db.delete("PORTS", where_clause, null)
        db.close()
        return result
    }


    fun addBoat(boat: Boat): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("boat_id", boat.boat_id)
        contentValues.put("name", boat.name)
        contentValues.put("description", boat.description)
        contentValues.put("price", boat.price)
        contentValues.put("capacity", boat.capacity)
        contentValues.put("availability", boat.availability)
        contentValues.put("port_id", boat.port?.port_id)
        val result = db.insert("BOATS", null, contentValues)
        db.close()
        return result
    }

    fun updateBoat(boat: Boat): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("boat_id", boat.boat_id)
        contentValues.put("name", boat.name)
        contentValues.put("description", boat.description)
        contentValues.put("price", boat.price)
        contentValues.put("capacity", boat.capacity)
        contentValues.put("availability", boat.availability)
        contentValues.put("port_id", boat.port?.port_id)
        val where_clause = "boat_id = ${boat.boat_id}"
        val result = db.update("BOATS", contentValues, where_clause, null)
        db.close()
        return result
    }

    fun deleteBoat(boat: Boat): Int {
        val db = this.writableDatabase
        val where_clause = "boat_id = ${boat.boat_id}"
        val result = db.delete("BOATS", where_clause, null)
        db.close()
        return result
    }


    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("user_id", user.user_id)
        contentValues.put("name", user.name)
        contentValues.put("surname", user.surname)
        contentValues.put("birthdate", user.birthdate)
        contentValues.put("gender", user.gender)
        contentValues.put("email", user.email)
        contentValues.put("phone", user.phone)
        contentValues.put("password", user.password)
        val result = db.insert("USERS", null, contentValues)
        db.close()
        return result
    }

    fun updateUser(user: User): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("user_id", user.user_id)
        contentValues.put("name", user.name)
        contentValues.put("surname", user.surname)
        contentValues.put("birthdate", user.birthdate)
        contentValues.put("gender", user.gender)
        contentValues.put("email", user.email)
        contentValues.put("phone", user.phone)
        val where_clause = "user_id = ${user.user_id}"
        val result = db.update("USERS", contentValues, where_clause, null)
        db.close()
        return result
    }

    fun deleteUser(user: User): Int {
        val db = this.writableDatabase
        val where_clause = "user_id = ${user.user_id}"
        val result = db.delete("USERS", where_clause, null)
        db.close()
        return result
    }


    fun addReachable(isReachable: IsReachable): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("port_id_1", isReachable.port1.port_id)
        contentValues.put("port_id_2", isReachable.port2.port_id)
        contentValues.put("distance", isReachable.distance)
        val result = db.insert("IS_REACHABLE_FROM", null, contentValues)
        db.close()
        return result
    }

    fun deleteReachable(isReachable: IsReachable): Int {
        val db = this.writableDatabase
        val where_clause =
            "port_id_1 = ${isReachable.port1.port_id} AND port_id_2 = ${isReachable.port2.port_id}"
        val result = db.delete("IS_REACHABLE_FROM", where_clause, null)
        db.close()
        return result
    }


    fun addRoute(route: Route): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("route_id", route.route_id)
        contentValues.put("length", route.length)
        contentValues.put("duration", route.duration)
        contentValues.put("user_id", route.user?.user_id)
        var result = db.insert("ROUTES", null, contentValues)
        contentValues.clear()
        if (result == -1L) return result
        for (port in route.consistingPorts) {
            contentValues.put("route_id", route.route_id)
            contentValues.put("port_id", port.port_id)
            result = db.insert("CONSISTS_OF", null, contentValues)
            if (result == -1L) return result
            contentValues.clear()
        }
        db.close()
        return result
    }

    fun updateRoute(route: Route): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("route_id", route.route_id)
        contentValues.put("length", route.length)
        contentValues.put("duration", route.duration)
        contentValues.put("user_id", route.user?.user_id)
        val where_clause = "route_id = ${route.route_id}"
        val result = db.update("ROUTES", contentValues, where_clause, null)
        contentValues.clear()
        if (result == -1) return result
        db.delete("CONSISTS_OF", "route_id = ${route.route_id}", null)
        for (port in route.consistingPorts) {
            contentValues.put("route_id", route.route_id)
            contentValues.put("port_id", port.port_id)
            val result2 = db.insert("CONSISTS_OF", null, contentValues).toInt()
            if (result2 == -1) return result2
            contentValues.clear()
        }
        db.close()
        return result
    }

    fun deleteRoute(route: Route): Int {
        val db = this.writableDatabase
        val where_clause = "route_id = ${route.route_id}"
        val result = db.delete("ROUTES", where_clause, null)
        db.close()
        return result
    }


    fun addTrip(trip: Trip): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("trip_id", trip.trip_id)
        contentValues.put("start_date", trip.start_date)
        contentValues.put("end_date", trip.end_date)
        contentValues.put("number_of_ppl", trip.number_of_ppl)
        contentValues.put("user_id", trip.user?.user_id)
        contentValues.put("route_id", trip.route?.route_id)
        contentValues.put("boat_id", trip.boat?.boat_id)
        val result = db.insert("TRIPS", null, contentValues)
        db.close()
        return result
    }

    fun updateTrip(trip: Trip): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("trip_id", trip.trip_id)
        contentValues.put("start_date", trip.start_date)
        contentValues.put("end_date", trip.end_date)
        contentValues.put("number_of_ppl", trip.number_of_ppl)
        contentValues.put("user_id", trip.user?.user_id)
        contentValues.put("route_id", trip.route?.route_id)
        contentValues.put("boat_id", trip.boat?.boat_id)
        val where_clause = "trip_id = ${trip.trip_id}"
        val result = db.update("TRIPS", contentValues, where_clause, null)
        db.close()
        return result
    }

    fun deleteTrip(trip: Trip): Int {
        val db = this.writableDatabase
        val where_clause = "route_id = ${trip.trip_id}"
        val result = db.delete("TRIPS", where_clause, null)
        db.close()
        return result
    }


    @SuppressLint("Range")
    fun getPort(id: Int): Port? {
        val db = this.readableDatabase
        val cursor: Cursor?
        val query = "SELECT * FROM PORTS WHERE port_id = $id"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return null
        }
        if (cursor.moveToFirst()) {
            val port_id = cursor.getInt(cursor.getColumnIndex("port_id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val description = cursor.getString(cursor.getColumnIndex("description"))
            val x_coordinate = cursor.getFloat(cursor.getColumnIndex("x_coordinate"))
            val y_coordinate = cursor.getFloat(cursor.getColumnIndex("y_coordinate"))
            val max_capacity = cursor.getInt(cursor.getColumnIndex("max_capacity"))
            val boat_capacity = cursor.getInt(cursor.getColumnIndex("port_id"))
            val port = Port(
                port_id,
                name,
                description,
                x_coordinate,
                y_coordinate,
                max_capacity,
                boat_capacity
            )
            cursor.close()
            return port
        }
        cursor.close()
        return null
    }

    @SuppressLint("Range")
    fun getPorts(): List<Port> {
        val db = this.readableDatabase
        val cursor: Cursor?
        val list = ArrayList<Port>()
        val query = "SELECT * FROM PORTS"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return list
        }
        if (cursor.moveToFirst()) {
            do {
                val port_id = cursor.getInt(cursor.getColumnIndex("port_id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val x_coordinate = cursor.getFloat(cursor.getColumnIndex("x_coordinate"))
                val y_coordinate = cursor.getFloat(cursor.getColumnIndex("y_coordinate"))
                val max_capacity = cursor.getInt(cursor.getColumnIndex("max_capacity"))
                val boat_capacity = cursor.getInt(cursor.getColumnIndex("port_id"))
                val port = Port(
                    port_id,
                    name,
                    description,
                    x_coordinate,
                    y_coordinate,
                    max_capacity,
                    boat_capacity
                )
                list.add(port)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }


    fun getBoat(id: Int): Boat? {
        val boats = getBoats()
        for (boat in boats) {
            if (boat.boat_id == id) {
                return boat
            }
        }
        return null
    }

    @SuppressLint("Range")
    fun getBoats(): List<Boat> {
        val db = this.readableDatabase
        val cursor: Cursor?
        val list = ArrayList<Boat>()
        val query = "SELECT * FROM BOATS"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return list
        }
        if (cursor.moveToFirst()) {
            do {
                val boat_id = cursor.getInt(cursor.getColumnIndex("boat_id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val price = cursor.getFloat(cursor.getColumnIndex("price"))
                val capacity = cursor.getInt(cursor.getColumnIndex("capacity"))
                val availability = cursor.getInt(cursor.getColumnIndex("availability"))
                val port_id = cursor.getInt(cursor.getColumnIndex("port_id"))
                val port = getPort(port_id)
                val boat = Boat(boat_id, name, description, price, capacity, availability, port)
                list.add(boat)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }


    @SuppressLint("Range")
    fun getUser(id: Int): User? {
        val db = this.readableDatabase
        val cursor: Cursor?
        val query = "SELECT * FROM USERS WHERE user_id = $id"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return null
        }
        cursor.moveToFirst()
        val user_id = cursor.getInt(cursor.getColumnIndex("user_id"))
        val name = cursor.getString(cursor.getColumnIndex("name"))
        val surname = cursor.getString(cursor.getColumnIndex("surname"))
        val birthdate = cursor.getString(cursor.getColumnIndex("birthdate"))
        val gender = cursor.getString(cursor.getColumnIndex("gender"))
        val email = cursor.getString(cursor.getColumnIndex("email"))
        val phone = cursor.getString(cursor.getColumnIndex("phone"))
        val password = cursor.getString(cursor.getColumnIndex("password"))
        cursor.close()
        return User(user_id, name, surname, birthdate, gender, email, phone, password)
    }


    @SuppressLint("Range")
    fun getReachablePorts(port1: Port?): List<Port?> {
        val db = this.readableDatabase
        val cursor: Cursor?
        val list = ArrayList<Port?>()
        val ids = ArrayList<Int>()
        val query = "SELECT * FROM IS_REACHABLE_FROM WHERE port_id_1 = ${port1?.port_id}"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return list
        }
        if (cursor.moveToFirst()) {
            do {
                val port_id = cursor.getInt(cursor.getColumnIndex("port_id_2"))
                ids.add(port_id)
            } while (cursor.moveToNext())
        }
        cursor.close()
        for (id in ids) {
            list.add(getPort(id))
        }
        return list
    }


    @SuppressLint("Range")
    fun getRoute(id: Int): Route? {
        val db = this.readableDatabase
        val cursor: Cursor?
        val query = "SELECT * FROM ROUTES WHERE route_id = $id"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return null
        }
        if (cursor.moveToFirst()) {
            val route_id = cursor.getInt(cursor.getColumnIndex("route_id"))
            val length = cursor.getFloat(cursor.getColumnIndex("length"))
            val duration = cursor.getInt(cursor.getColumnIndex("duration"))
            val user_id = cursor.getInt(cursor.getColumnIndex("user_id"))
            val user = getUser(user_id)
            val ports = getConsistingPorts(route_id) as ArrayList
            val route = Route(route_id, length, duration, user, ports)
            cursor.close()
            return route
        }
        cursor.close()
        return null
    }

    @SuppressLint("Range")
    fun getRoutes(): List<Route> {
        val db = this.readableDatabase
        val cursor: Cursor?
        val list = ArrayList<Route>()
        val query = "SELECT * FROM ROUTES"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return list
        }
        if (cursor.moveToFirst()) {
            do {
                val route_id = cursor.getInt(cursor.getColumnIndex("route_id"))
                val length = cursor.getFloat(cursor.getColumnIndex("length"))
                val duration = cursor.getInt(cursor.getColumnIndex("duration"))
                val user_id = cursor.getInt(cursor.getColumnIndex("user_id"))
                val user = getUser(user_id)
                val ports = getConsistingPorts(route_id) as ArrayList
                val route = Route(route_id, length, duration, user, ports)
                list.add(route)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    @SuppressLint("Range")
    fun getConsistingPorts(id: Int): List<Port> {
        val db = this.readableDatabase
        val cursor: Cursor?
        val ids = ArrayList<Int>()
        val list = ArrayList<Port>()
        val query = "SELECT * FROM CONSISTS_OF WHERE route_id = $id"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return list
        }
        if (cursor.moveToFirst()) {
            do {
                val port_id = cursor.getInt(cursor.getColumnIndex("port_id"))
                ids.add(port_id)
            } while (cursor.moveToNext())
        }
        cursor.close()
        for (port_id in ids) {
            val port = getPort(port_id)
            if (port != null) {
                list.add(port)
            }
        }
        return list
    }

    @SuppressLint("Range")
    fun getTrips(): List<Trip> {
        val db = this.readableDatabase
        val cursor: Cursor?
        val list = ArrayList<Trip>()
        val query = "SELECT * FROM TRIPS"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return list
        }
        if (cursor.moveToFirst()) {
            do {
                val trip_id = cursor.getInt(cursor.getColumnIndex("trip_id"))
                val start_date = cursor.getString(cursor.getColumnIndex("start_date"))
                val end_date = cursor.getString(cursor.getColumnIndex("end_date"))
                val number_of_ppl = cursor.getInt(cursor.getColumnIndex("end_date"))
                val user_id = cursor.getInt(cursor.getColumnIndex("user_id"))
                val user = getUser(user_id)
                val route_id = cursor.getInt(cursor.getColumnIndex("route_id"))
                val route = getRoute(route_id)
                val boat_id = cursor.getInt(cursor.getColumnIndex("boat_id"))
                val boat = getBoat(boat_id)
                val trip = Trip(trip_id, start_date, end_date, number_of_ppl, user, route, boat)
                list.add(trip)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    @SuppressLint("Range")
    fun getTrip(id: Int): Trip? {
        val db = this.readableDatabase
        val cursor: Cursor?
        val query = "SELECT * FROM TRIPS WHERE trip_id = $id"
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(query)
            return null
        }
        if (cursor.moveToFirst()) {
            val trip_id = cursor.getInt(cursor.getColumnIndex("trip_id"))
            val start_date = cursor.getString(cursor.getColumnIndex("start_date"))
            val end_date = cursor.getString(cursor.getColumnIndex("end_date"))
            val number_of_ppl = cursor.getInt(cursor.getColumnIndex("end_date"))
            val user_id = cursor.getInt(cursor.getColumnIndex("user_id"))
            val user = getUser(user_id)
            val route_id = cursor.getInt(cursor.getColumnIndex("route_id"))
            val route = getRoute(route_id)
            val boat_id = cursor.getInt(cursor.getColumnIndex("boat_id"))
            val boat = getBoat(boat_id)
            val trip = Trip(trip_id, start_date, end_date, number_of_ppl, user, route, boat)
            cursor.close()
            return trip
        }
        cursor.close()
        return null
    }
}