package com.example.almin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.transaction_row.view.*

class RecyclerViewAdapter(private var textLedger : ArrayList<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.transaction_row,
                parent,
                false

            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val transaction = textLedger[position]
        holder.itemView.apply {
            transaction_tv.text = transaction
        }
    }

    override fun getItemCount(): Int = textLedger.size
}