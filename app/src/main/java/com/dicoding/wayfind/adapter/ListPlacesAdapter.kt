package com.dicoding.wayfind.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.wayfind.R
import com.dicoding.wayfind.data.Places


class ListPlacesAdapter(private val listPlaces: ArrayList<Places>) : RecyclerView.Adapter<ListPlacesAdapter.ListViewHolder> (){
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.image_place)
        val namePlace: TextView = itemView.findViewById(R.id.name_place)
        val ratingPlace : TextView = itemView.findViewById(R.id.rating_place)
        val timePlace: TextView = itemView.findViewById(R.id.clock_place)
        val locationPlace: TextView = itemView.findViewById(R.id.location_place)
        val pricePlace: TextView = itemView.findViewById(R.id.price_place)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_places, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listPlaces.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (photo, name, rating, time, location, price) = listPlaces[position]
        Glide.with(holder.itemView.context)
            .load(photo) // URL Gambar
            .into(holder.imgPhoto) // imageView mana yang akan diterapkan
        holder.namePlace.text = name
        holder.ratingPlace.text = rating
        holder.timePlace.text = time
        holder.locationPlace.text = location
        holder.pricePlace.text = price
    }
}