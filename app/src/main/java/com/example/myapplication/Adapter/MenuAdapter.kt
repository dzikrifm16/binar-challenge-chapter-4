package com.example.myapplication.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataClass.ItemMenu
import com.example.myapplication.R

class MenuAdapter(
	private val listmenu: ArrayList<ItemMenu>,
	var gridType: Boolean = true,
	var onItemClick: ((ItemMenu) -> Unit)? = null
) :
	RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

	// Membuat Holder
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutResId = if(gridType) R.layout.list_item_menu else R.layout.grid_item_menu
		val view : View = LayoutInflater.from(parent.context).inflate(layoutResId,parent,false)
		return ViewHolder(view)

	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val (name, price, image) = listmenu[position]
		holder.name.text = name
		holder.price.text = price.toString()
		holder.image.setImageResource(image)

		val currentItem = listmenu[position]
		holder.itemView.setOnClickListener {
			onItemClick?.invoke(currentItem)
		}

	}

	override fun getItemCount(): Int {
		return listmenu.size
	}

	//Class holder
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val name = itemView.findViewById<TextView>(R.id.title_menu)!!
		val price = itemView.findViewById<TextView>(R.id.price_menu)!!
		val image = itemView.findViewById<ImageView>(R.id.image_menu)!!

	}

	@SuppressLint("NotifyDataSetChanged")
	fun reloadData(newData: ArrayList<ItemMenu>) {
		listmenu.addAll(newData)
		notifyDataSetChanged()
	}
}