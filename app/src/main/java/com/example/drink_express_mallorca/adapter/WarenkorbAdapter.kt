package com.example.drink_express_mallorca.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.drink_express_mallorca.MainViewModel
import com.example.drink_express_mallorca.R
import com.example.drink_express_mallorca.data.Repository
import com.example.drink_express_mallorca.data.model.WarenkorbItem

class WarenkorbAdapter(val viewModel: MainViewModel): RecyclerView.Adapter<WarenkorbAdapter.viewHolder>() {

    private var dataset = mutableListOf<WarenkorbItem>()

    class viewHolder(view: View): ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.warkorb_image_iv)
        val plusbtn: ImageButton = view.findViewById(R.id.warkorb_plus_ibtn)
        val minusbtn: ImageButton = view.findViewById(R.id.warkorb_minus_ibtn)
        val löschenbtn: ImageButton = view.findViewById(R.id.warkorb_löschen_btn)
        val anzahl: TextView = view.findViewById(R.id.warkorb_totalDrinks_tv)
    }

    fun submitWarenkorbList(list: MutableList<WarenkorbItem>){
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.warenkorb_item, parent, false)
        return viewHolder(layout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val item = dataset[position]
        Log.d("adapter", dataset.toString())

        holder.image.load(item.image)
        holder.anzahl.text = item.anzahl.toString()

        holder.minusbtn.setOnClickListener {
            if (viewModel.warenkorb[position].anzahl < 1){
                viewModel.warenkorb[position].anzahl = 1
            } else {
                viewModel.warenkorb[position].anzahl--
                viewModel.preisKalkulation(dataset)
                notifyDataSetChanged()
            }
        }

        holder.plusbtn.setOnClickListener {
            viewModel.warenkorb[position].anzahl++
            viewModel.preisKalkulation(dataset)
            notifyDataSetChanged()
        }

        holder.löschenbtn.setOnClickListener {
            itemLöschen(position)
            viewModel.preisKalkulation(dataset)
            Log.d("adapter", dataset.toString())
        }
    }

    private fun itemLöschen(position: Int){
        dataset.removeAt(position)
        notifyDataSetChanged()
    }
}