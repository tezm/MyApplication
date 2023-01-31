package com.example.myapplication

import androidx.test.core.app.ApplicationProvider
import org.junit.Test

class DatabaseTests {
    private val db = DatabaseHelper(ApplicationProvider.getApplicationContext())

    @Test
    fun addUser_isCorrect() {
        assert(true)
    }
}