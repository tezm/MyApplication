package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseHelper
    private lateinit var adapter: TripsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DatabaseHelper(this)
        adapter =TripsListAdapter(this, db)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db.populateIfNeeded()

        binding.addButton.setOnClickListener {
            val myIntent = Intent(this, NewTripActivity::class.java)
            startActivity(myIntent)
        }

        binding.FirstRecycleView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }
}