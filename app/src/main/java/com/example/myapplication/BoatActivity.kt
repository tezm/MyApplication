package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityBoatBinding

class BoatActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBoatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityBoatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BoatsRecycleView.adapter =BoatsListAdapter(this, DatabaseHelper(this))

        binding.goBackBoats.setOnClickListener{
            setResult(RESULT_CANCELED, null)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_boat)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}