package com.example.registercontacts.View

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.registercontacts.databinding.FragmentRegisterContactsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class RegisterContactsFragment : Fragment() {
    private var db = Firebase.firestore
    private var _binding:FragmentRegisterContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterContactsBinding.inflate(inflater, container, false)
        val view = binding.root
        getFields()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

        binding.btnSalvar.setOnClickListener {

            val email = binding.emailInput.text.toString()
            val name = binding.nameInput.text.toString()
            val phone = binding.phoneInput.text.toString()
            val birthDate = binding.birthInput.text.toString()
            val job = binding.jobInput.text.toString()
            val postalAddress = binding.postalAddressInput.text.toString()


            val allFieldsValid: Boolean =
                validateFields(email, name, phone, job, birthDate, postalAddress)

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
        }
    }

    private fun validateFields(
        email: String,
        name: String,
        phone: String,
        job: String,
        birthDate: String,
        postalAddress: String
    ): Boolean {
        var allFieldsValid = true

        if (name.isEmpty()) {
            binding.nameInput.setError("O nome é obrigatório")
            allFieldsValid = false
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.setError("Insira um e-mail válido")
            allFieldsValid = false
        }

        if (!validatePhone(phone)) {
            binding.phoneInput.setError("Telefone inválido\nExemplo: (xx) xxxxx-xxxx")
            allFieldsValid = false
        }

        if (!validatePostalAddress(postalAddress)) {
            binding.postalAddressInput.setError("CEP inválido\nExemplo: xxxxxxxx")
            allFieldsValid = false
        }

        if (job.isEmpty()) {
            binding.jobInput.setError("O campo de trabalho é obrigatório")
            allFieldsValid = false
        }

        if (!validateDateOfBirth(birthDate)) {
            binding.birthInput.setError("Data de nascimento inválido\nEx: dd/mm/yyyy")
            allFieldsValid = false
        }

        return allFieldsValid
    }

    private fun validatePhone(phone: String): Boolean {
        val regex = """\(\d{2}\)\s\d{4,5}-\d{4}""".toRegex()
        return regex.matches(phone)
    }

    private fun validatePostalAddress(postalAddress: String): Boolean {
        return postalAddress.matches("\\d{8}".toRegex())
    }

    private fun validateDateOfBirth(dateOfBirth: String): Boolean {
                if (dateOfBirth.isEmpty()) {
            return false
        }

        try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            dateFormat.isLenient = false
            dateFormat.parse(dateOfBirth)
        } catch (e: ParseException) {
            return false
        }
        return true
    }

    private fun saveContactsDB(contact: HashMap<String, String>) {

        val id = UUID.randomUUID().toString()
        db.collection("contacts").document(id)
            .set(contact)
            .addOnSuccessListener {
                clearFields()
                Toast.makeText(
                    activity,
                    "Contato salvo com sucesso!!",
                    Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    activity,
                    "Falha ao salvar contato, tente novamente!",
                    Toast.LENGTH_LONG).show()
            }
    }

    private fun basicAlert(msg: String) {
        val builder = AlertDialog.Builder(activity)
        with(builder)
        {
            setTitle("Atenção")
            setMessage(msg)
            setPositiveButton("OK", null)
            show()
        }
    }



}