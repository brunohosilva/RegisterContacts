package com.example.registercontacts.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.registercontacts.Adapter.CurrentContact
import com.example.registercontacts.Model.Contact
import com.example.registercontacts.R
import com.example.registercontacts.databinding.FragmentRegisterContactsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.UUID


class RegisterContactsFragment : Fragment() {
    private var db = Firebase.firestore
    private var _binding:FragmentRegisterContactsBinding? = null
    private val binding get() = _binding!!
    private val contact: Contact? = CurrentContact.contact

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterContactsBinding.inflate(inflater, container, false)
        val view = binding.root
        getFields()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contact?.let { c ->
            fillContactFields(c)
        }
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

    private fun fillContactFields(contact: Contact) {
        binding.nameInput.setText(contact.name)
        binding.emailInput.setText(contact.email)
        binding.phoneInput.setText(contact.phone)
        binding.jobInput.setText(contact.job)
        binding.postalAddressInput.setText(contact.postalAddress)
        binding.birthInput.setText(contact.birthDate)
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
            binding.nameInput.error = getString(R.string.error_register_name)
            allFieldsValid = false
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = getString(R.string.error_register_mail)
            allFieldsValid = false
        }

        if (!validatePhone(phone)) {
            binding.phoneInput.error = getString(R.string.error_register_phone)
            allFieldsValid = false
        }

        if (!validatePostalAddress(postalAddress)) {
            binding.postalAddressInput.error = getString(R.string.error_register_cep)
            allFieldsValid = false
        }

        if (job.isEmpty()) {
            binding.jobInput.error = getString(R.string.error_register_job)
            allFieldsValid = false
        }

        if (!validateDateOfBirth(birthDate)) {
            binding.birthInput.error = getString(R.string.error_register_date)
            allFieldsValid = false
        }

        return allFieldsValid
    }

    private fun validatePhone(phone: String): Boolean {
        val regex = """\(\d{2}\)\s\d{4,5}-\d{4}""".toRegex()
        return regex.matches(phone)
    }

    private fun validatePostalAddress(postalAddress: String): Boolean {
        return postalAddress.matches("^\\d{5}-\\d{3}$".toRegex())
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

        if(this.contact != null) {
            getFields()
            val userRef = db.collection("contacts").document(this.contact.id!!)
            userRef.update(contact as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(
                        activity,
                        getString(R.string.contact_edit_done),
                        Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        activity,
                        getString(R.string.contact_edit_error),
                        Toast.LENGTH_LONG).show()
                }

        } else {
            val id = UUID.randomUUID().toString()
            db.collection("contacts").document(id)
                .set(contact)
                .addOnSuccessListener {
                    clearFields()
                    Toast.makeText(
                        activity,
                        getString(R.string.contact_register_done),
                        Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        activity,
                        getString(R.string.contact_register_error),
                        Toast.LENGTH_LONG).show()
                }
        }
    }
}