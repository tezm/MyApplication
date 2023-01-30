package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityNewTripBinding


class NewTripActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNewTripBinding
    val route = Route(0, 0f, 0, null, ArrayList())
    private var trip = Trip(0, "", "", 0, null, route, null)
    private var db = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButtonTripPage.setOnClickListener {
            val intent = Intent(this, PortsActivity::class.java)
            val portCount = trip.route?.consistingPorts?.size ?: 0
            if (portCount > 0) {
                intent.putExtra(
                    "port_id",
                    trip.route?.consistingPorts?.get(portCount - 1)?.port_id
                )
            }

            startActivityForResult(intent, 1)
        }

        binding.goOn.setOnClickListener{
            val intent = Intent(this, BoatActivity::class.java)
            startActivityForResult(intent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // portsActivity
            val nextPortId = data?.getIntExtra("port_id", -1) ?: -1
            val nextPort = db.getPort(nextPortId )!!
            trip.route?.consistingPorts?.add(nextPort)

            // Update the ports field.
            var portsText = "Ports: "
            val ports = (trip?.route?.consistingPorts)!!
            portsText += ports.joinToString(", ") { it.name }
            binding.textViewHere.text =portsText
        }

        if(requestCode == 2 && resultCode == RESULT_OK) {
            // confirm button pressed and boat selected.
            trip.start_date = binding.from.text.toString()
            trip.end_date = binding.ToField.text.toString()
            val user = db.getUser(1)!! // We assume that one exists, unimplemented use case.
            trip.user = user
            trip.number_of_ppl = binding.NumberOfPeople.text.toString().toIntOrNull() ?: 1 // We should validate this.
            val db = DatabaseHelper(this)
            db.addTrip(trip)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_new_trip)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}