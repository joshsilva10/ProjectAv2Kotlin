package com.example.projectav2;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var titles:List<String>, private var desc: List<String>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

        inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val  itemTitle : TextView = itemView.findViewById(R.id.tv_title)
        val  itemDesc : TextView = itemView.findViewById(R.id.tv_desc)

        init {
        itemView.setOnClickListener { v : View ->
        val position: Int = adapterPosition
         val tit = itemTitle.text.toString()
         val des = itemDesc.text.toString()


        Toast.makeText(itemView.context, "you clicked on ${itemTitle.text} in prosition ${position+1}, ${itemDesc.text}", Toast.LENGTH_LONG).show()
        }
        }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_list_order, parent,false)
        return ViewHolder(v)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDesc.text = desc[position]
        }

        override fun getItemCount(): Int {
        return titles.size

        }


}
