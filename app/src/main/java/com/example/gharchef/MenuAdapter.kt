package com.example.gharchef

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MenuAdapter(
    private val items: List<MenuItemData>,
    private val onAddToCartClick: (MenuItemData) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage:  ImageView = view.findViewById(R.id.ivGridImage)
        val tvName:   TextView  = view.findViewById(R.id.tvGridName)
        val tvPrice:  TextView  = view.findViewById(R.id.tvGridPrice)
        val tvTime:   TextView  = view.findViewById(R.id.tvGridTime)
        val btnAdd:   Button    = view.findViewById(R.id.btnAddGrid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid_dish, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = items[position]

        holder.tvName.text  = item.name
        holder.tvPrice.text = "₹%.0f".format(item.price)
        holder.tvTime.text  = "⏱ ${item.prepTime}"   // ← prepTime, not cookingTime

        if (item.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.bg_image_placeholder)
                .centerCrop()
                .into(holder.ivImage)
        }

        holder.btnAdd.setOnClickListener { onAddToCartClick(item) }
    }

    override fun getItemCount(): Int = items.size
}