package com.example.drink_express_mallorca.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.drink_express_mallorca.R
import com.example.drink_express_mallorca.data.model.Drink
import com.example.drink_express_mallorca.ui.SucheFragmentDirections

class SucheAdapter: RecyclerView.Adapter<SucheAdapter.viewHolder>() {

    private var dataset = listOf<Drink>()

    class viewHolder(view: View): ViewHolder(view){
        val cardView: CardView = view.findViewById(R.id.drinkCard_cv)
        val image: ImageView = view.findViewById(R.id.drinkCard_iv)
        val titel: TextView = view.findViewById(R.id.drinkCard_title_tv)
    }

    fun submitList(list: List<Drink>){
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.drinkcardview_item, parent, false)
        return viewHolder(layout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val getraenk = dataset[position]

        holder.image.load(getraenk.strDrinkThumb)
        holder.titel.text = getraenk.strDrink
        holder.cardView.setOnClickListener {
            Navigation.findNavController(holder.itemView)
                .navigate(SucheFragmentDirections
                    .actionSucheFragmentToDetailFragment(getraenk.strDrink!!))
        }
    }
}