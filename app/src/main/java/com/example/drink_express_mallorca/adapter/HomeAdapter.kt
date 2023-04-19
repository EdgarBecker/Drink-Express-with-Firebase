package com.example.drink_express_mallorca.adapter

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.drink_express_mallorca.R
import com.example.drink_express_mallorca.data.model.Drink
import com.example.drink_express_mallorca.ui.HomeFragmentDirections

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.viewHolder>() {

    private var dataset = mutableListOf<Drink>()

    class viewHolder(view:View): ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.home_suggestionImage_iv)
        val title: TextView = view.findViewById(R.id.home_suggestionTitle_tv)
    }

    fun submitList(list: MutableList<Drink>){
        dataset = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.homesuggestion_item, parent, false)
        return viewHolder(layout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val getraenk = dataset[position]

        holder.image.load(getraenk.strDrinkThumb){
            transformations(RoundedCornersTransformation(10F))
        }
        holder.title.text = getraenk.strDrink
        holder.image.setOnClickListener {
            Navigation.findNavController(holder.itemView)
                .navigate(HomeFragmentDirections
                    .actionHomeFragmentToDetailFragment(getraenk.strDrink!!))
        }
    }

}