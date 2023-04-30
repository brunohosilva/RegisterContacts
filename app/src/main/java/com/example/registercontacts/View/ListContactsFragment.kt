package com.example.registercontacts.View

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registercontacts.Adapter.ContactAdapter
import com.example.registercontacts.Adapter.CurrentContact
import com.example.registercontacts.Model.Contact
import com.example.registercontacts.R
import com.example.registercontacts.databinding.ContactItemBinding
import com.example.registercontacts.databinding.FragmentListContactsBinding
import com.example.registercontacts.databinding.FragmentRegisterContactsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListContactsFragment : Fragment(), ContactAdapter.OnEditClickListener {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentListContactsBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentListContactsBinding.inflate(inflater, container, false)
        return binding.root
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
            .addSnapshotListener { documents, key ->
                contactList = arrayListOf()
                if (documents != null) {
                    documents.map { document ->
                        val contact = Contact(
                            id = document.id,
                            name = document.getString("name"),
                            email = document.getString("email"),
                            phone = document.getString("phone"),
                            job = document.getString("job"),
                            postalAddress = document.getString("postalAddress"),
                            birthDate = document.getString("birthDate")
                        )
                        contactList.add(contact)
                    }
                }
                recyclerView.adapter = ContactAdapter(contactList, this)
            }
    }

    override fun onEditClick(contact: Contact) {
        if (CurrentContact.contact != null) {
            val registerContactsFragment = RegisterContactsFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, registerContactsFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}