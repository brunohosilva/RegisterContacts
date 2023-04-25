package com.example.registercontacts.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.registercontacts.Model.Contact
import com.example.registercontacts.R

class ContactAdapter(
    private val contactList: ArrayList<Contact>
) : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentContact = contactList[position]
        holder.contactName.text = currentContact.name
        holder.contactPhone.text = currentContact.phone
        holder.contactJob.text = currentContact.job
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class MyViewHolder(contactView: View) : RecyclerView.ViewHolder(contactView) {
        val contactName: TextView = itemView.findViewById(R.id.tvcontactName)
        val contactPhone: TextView = itemView.findViewById(R.id.tvcontactPhone)
        val contactJob: TextView = itemView.findViewById(R.id.tvcontactJob)
    }
}