package com.example.registercontacts.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registercontacts.Adapter.ContactAdapter
import com.example.registercontacts.Model.Contact
import com.example.registercontacts.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListContactsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var contactList: ArrayList<Contact>
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactInit()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
    }

    private fun contactInit() {
        db = FirebaseFirestore.getInstance()
        db.collection("contacts")
            .addSnapshotListener { documents, _ ->
                contactList = arrayListOf()
                if (documents != null) {
                    for (contact in documents) {
                        val newContact: Contact = contact.toObject(Contact::class.java)
                        contactList.add(newContact)
                    }
                }
                recyclerView.adapter = ContactAdapter(contactList)
            }
    }
}