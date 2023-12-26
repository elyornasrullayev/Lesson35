package com.ensoft.lesson35

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class HorizontalRecyclerAdapter(val list: MutableList<HorizontalItem>) : RecyclerView.Adapter<HorizontalRecyclerAdapter.HorizontalViewHolder>(){

    lateinit var listener: OnItemClickedListener

    inner class HorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: HorizontalItem){
            val image_view = itemView.findViewById<CircleImageView>(R.id.image_view)
            val tv_item = itemView.findViewById<TextView>(R.id.tv_item)
            image_view.setImageDrawable(item.image)
            tv_item.text = item.title

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION){
                    listener.onItemClicked(adapterPosition)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val view =   LayoutInflater.from(parent.context).inflate(R.layout.horizontal_item,parent,false)
        return HorizontalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int)  {
        holder.bind(list[position])
    }

    fun setOnItemClickedListener(listener: OnItemClickedListener){
        this.listener = listener
    }

    interface OnItemClickedListener{
        fun onItemClicked(position: Int)
    }


}