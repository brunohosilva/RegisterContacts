package com.example.registercontacts.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.registercontacts.R
import com.example.registercontacts.databinding.ActivityRegisterContactsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class RegisterContacts : AppCompatActivity() {
    private var db = Firebase.firestore
    private lateinit var binding: ActivityRegisterContactsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_contacts)

        binding = ActivityRegisterContactsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getFields()
    }

    private fun getFields() {
        binding.btnSalvar.setOnClickListener(View.OnClickListener {

            val email = binding.emailInput.text.toString()
            val name = binding.nameInput.text.toString()
            val phone = binding.phoneInput.text.toString()

            val contact = hashMapOf(
                "name" to name,
                "phone" to phone,
                "email" to email
            )

            saveContactsDB(contact)
        })
    }

    private fun saveContactsDB(contact: HashMap<String, String>) {
        val id = UUID.randomUUID().toString()

        db.collection("contacts").document(id)
            .set(contact)
            .addOnSuccessListener {
                Toast.makeText(
                    this.applicationContext,
                    "Contato salvo com sucesso!!",
                    Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this.applicationContext,
                    "Falha ao salvar contato, tente novamente!",
                    Toast.LENGTH_LONG).show()
            }
    }
}