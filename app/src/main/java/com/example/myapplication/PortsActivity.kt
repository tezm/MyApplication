package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.myapplication.databinding.ActivityPortsBinding

class PortsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPortsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var lastPortId: Int? = intent.getIntExtra("port_id", -1)
        if(lastPortId == -1) {
            lastPortId = null
        }

        val adapter = PortsListAdapter(this, DatabaseHelper(this), lastPortId)

        binding = ActivityPortsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.PortsRecycleView.adapter = adapter

        binding.goBackPorts.setOnClickListener{
            setResult(RESULT_CANCELED, null)
            finish()
        }
    }


}
