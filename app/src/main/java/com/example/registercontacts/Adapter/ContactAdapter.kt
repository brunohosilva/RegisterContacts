package com.example.registercontacts.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.registercontacts.Model.Contact
import com.example.registercontacts.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
object CurrentContact {
    var contact: Contact? = null
}
class ContactAdapter(
    private val contactList: ArrayList<Contact>,
    private val onEditClickListener: OnEditClickListener
) : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    interface OnEditClickListener {
        fun onEditClick(contact: Contact)
    }

    private var db = Firebase.firestore
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

        val keyContact = currentContact.id.toString()

        holder.btnDelete.setOnClickListener {
            db = FirebaseFirestore.getInstance()
            db.collection("contacts").document(keyContact).delete()
        }

        holder.btnEdit.setOnClickListener {
            val currentContactToEdit = contactList[position]
            CurrentContact.contact = currentContactToEdit
            onEditClickListener.onEditClick(currentContactToEdit)
        }

    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class MyViewHolder(contactView: View) : RecyclerView.ViewHolder(contactView) {
        val contactName: TextView = itemView.findViewById(R.id.tvcontactName)
        val contactPhone: TextView = itemView.findViewById(R.id.tvcontactPhone)
        val contactJob: TextView = itemView.findViewById(R.id.tvcontactJob)
        var btnDelete: ImageView = itemView.findViewById(R.id.btn_apagar)
        var btnEdit: ImageView = itemView.findViewById(R.id.btn_editar)
    }
}