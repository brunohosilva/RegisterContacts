package com.example.registercontacts.View

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registercontacts.R
import com.example.registercontacts.databinding.ActivityRegisterContactsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

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

    private fun clearFields() {
        binding.emailInput.setText("")
        binding.phoneInput.setText("")
        binding.nameInput.setText("")
        binding.postalAddressInput.setText("")
        binding.birthInput.setText("")
        binding.jobInput.setText("")
    }

    private fun getFields() {

        binding.btnSalvar.setOnClickListener(View.OnClickListener {

            val email = binding.emailInput.text.toString()
            val name = binding.nameInput.text.toString()
            val phone = binding.phoneInput.text.toString()
            val birthDate = binding.birthInput.text.toString()
            val job = binding.jobInput.text.toString()
            val postalAddress = binding.postalAddressInput.text.toString()


            val allFieldsValid: Boolean = validateFields(email, name, phone, birthDate, job, postalAddress)

            if (allFieldsValid) {
                val contact = hashMapOf(
                    "name" to name,
                    "phone" to phone,
                    "email" to email,
                    "birthDate" to birthDate,
                    "job" to job,
                    "postalAddress" to postalAddress
                )

                saveContactsDB(contact)
            }
        })
    }

    private fun validateFields(
        email: String,
        name: String,
        phone: String,
        job: String,
        birthDate: String,
        postalAddress: String
    ): Boolean {

        if(
            email.isEmpty() or
            phone.isEmpty() or
            name.isEmpty() or
            job.isEmpty() or
            birthDate.isEmpty() or
            postalAddress.isEmpty()
        ){
            basicAlert("Preencher todos os campos")
            return false
        } else if (!isValidEmail(email)) {
            basicAlert("E-mail inválido")
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }

    private fun saveContactsDB(contact: HashMap<String, String>) {

        val id = UUID.randomUUID().toString()
        db.collection("contacts").document(id)
            .set(contact)
            .addOnSuccessListener {
                clearFields()
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

    private fun basicAlert(msg: String) {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Atenção")
            setMessage(msg)
            setPositiveButton("OK", null)
            show()
        }
    }
}