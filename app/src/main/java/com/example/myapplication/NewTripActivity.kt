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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val nextPortId = data?.getIntExtra("port_id", -1) ?: -1
            val nextPort = db.getPort(nextPortId )!!
            trip.route?.consistingPorts?.add(nextPort)

            // Update the ports field.
            var portsText = "Ports: "
            val ports = (trip?.route?.consistingPorts)!!
            portsText += ports.joinToString(", ") { it.name }
            binding.textViewHere.text =portsText
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_new_trip)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}