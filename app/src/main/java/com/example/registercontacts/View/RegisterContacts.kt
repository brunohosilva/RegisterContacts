package com.example.registercontacts.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.registercontacts.R
import com.example.registercontacts.databinding.ActivityRegisterContactsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterContacts : AppCompatActivity() {
    private var db = Firebase.firestore
    private lateinit var binding: ActivityRegisterContactsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_contacts)

        binding = ActivityRegisterContactsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSalvar.setOnClickListener(View.OnClickListener {
            Log.e("asd", "asd")
            val city = hashMapOf(
                "name" to "Los Angeles",
                "state" to "CA",
                "country" to "USA"
            )

            db.collection("contacts").document("register_contacts")
                .set(city)
                .addOnSuccessListener { Log.d("---->", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w("---->", "Error writing document", e) }
        })
    }
}