package com.example.androidmysql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class ContactAdapter(
    val contacts: ArrayList<ReadModel.Data>,
    val listener: OnAdapterListener
): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder (
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_contact, parent, false)
        )
    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder,
                                  position: Int) {
        val data = contacts[position]
        holder.tvName.text = data.name
        holder.tvNumber.text = data.number
        holder.itemView.setOnClickListener {
            listener.onUpdate(data)
        }
        holder.imgDelete.setOnClickListener {
            listener.onDelete(data)
        }
    }
    override fun getItemCount() = contacts.size
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.TextViewName)
        val tvNumber = view.findViewById<TextView>(R.id.TextViewNumber)
        val imgDelete =
            view.findViewById<ImageView>(R.id.ImageViewDelete)
    }
    public fun setData(data: List<ReadModel.Data>) {
        contacts.clear()
        contacts.addAll(data)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onUpdate(contact: ReadModel.Data)
        fun onDelete(contact: ReadModel.Data)
    }
}