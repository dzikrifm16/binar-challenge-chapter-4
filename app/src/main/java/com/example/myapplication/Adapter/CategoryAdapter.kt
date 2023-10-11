package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataClass.Category
import com.example.myapplication.R

class 	CategoryAdapter(
	private val listkategori: ArrayList<Category>
) :
	RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val image = itemView.findViewById<ImageView>(R.id.category_image_menu)!!
		val name = itemView.findViewById<TextView>(R.id.titleCat)!!

	}

	// Membuat Holder
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.category_item_menu, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val (name,image) = listkategori	[position]
		holder.name.text = name
		holder.image.setImageResource(image)

	}

	override fun getItemCount(): Int {
		return listkategori.size
	}

}