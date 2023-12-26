package com.ensoft.lesson35

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.google.android.material.snackbar.Snackbar

class RecyclerViewAdapter(val itemList: MutableList<Item>): RecyclerView.Adapter<RecyclerViewAdapter.viewHolder>() {


    val viewBinderHelper = ViewBinderHelper()



    inner class viewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val swipeRevealLayout = itemview.findViewById<SwipeRevealLayout>(R.id.swipeRevealLayout)

        fun bind(item: Item){
            val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
            val btn_delete = itemView.findViewById<CardView>(R.id.btn_delete)

            tv_title.text = item.text

            btn_delete.setOnClickListener {
                val position = adapterPosition
                val deleteItem = itemList[position]
                itemList.removeAt(position)
                notifyItemRemoved(position)

                //snackbar for undo
                Snackbar.make(itemView, "Item has been removed",
                Snackbar.LENGTH_LONG).setAction("UNDO"){
                    itemList.add(position, deleteItem)
                    notifyItemInserted(position)
                }.setActionTextColor(Color.RED).show()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return  viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.swipeRevealLayout, itemList[position].text)
        viewBinderHelper.closeLayout(itemList[position].text)


        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}