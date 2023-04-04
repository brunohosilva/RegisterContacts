package com.example.registercontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.registercontacts.View.Contacts
import com.example.registercontacts.View.Login
import com.example.registercontacts.View.RegisterContacts
import com.example.registercontacts.View.SignUp
import com.example.registercontacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpListener()





    }

    private fun setUpListener() {
        binding.loginScreen.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Login ::class.java)
            startActivity(intent)
        })

        binding.contactsListScreen.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Contacts ::class.java)
            startActivity(intent)
        })

        binding.registerNewContactsScreen.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegisterContacts ::class.java)
            startActivity(intent)
        })

        binding.signupScreen.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SignUp ::class.java)
            startActivity(intent)
        })
    }


}