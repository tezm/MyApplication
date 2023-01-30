package com.example.myapplication

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class BoatsListAdapter(
    val context: Context,
    val db: DatabaseHelper,
) : RecyclerView.Adapter<BoatsListAdapter.BoatViewHolder>() {
    val boats = db.getBoats()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout, parent, false)

        return BoatViewHolder(view)
    }


    override fun onBindViewHolder(holder: BoatViewHolder, position: Int) {
        val boat = boats[position]
        holder.textView.text = boat?.name ?: ""

        holder.textView.setOnClickListener {
            var intent = Intent()

            intent.putExtra("boat_id", boat?.boat_id ?: -1)
            val activity =context as BoatActivity
            activity.setResult(RESULT_OK, intent)
            activity.finish()
        }
    }

    override fun getItemCount(): Int {
        return boats.size
    }

    class BoatViewHolder(
        val view: View
    ) : ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.list_item_text_view)
    }
}