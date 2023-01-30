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

class TripsListAdapter(
    val context: Context,
    val db: DatabaseHelper,
) : RecyclerView.Adapter<TripsListAdapter.TripViewHolder>() {
    var trips = db.getTrips()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout, parent, false)

        return TripViewHolder(view)
    }


    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = trips[position]!!
        holder.textView.text = trip.start_date + " - " + trip.end_date
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    public fun refresh() {
        trips = db.getTrips()
        notifyDataSetChanged()
    }

    class TripViewHolder(
        val view: View
    ) : ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.list_item_text_view)
    }
}