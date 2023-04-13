package com.example.registercontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.registercontacts.View.*
import com.example.registercontacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragment(ListContactsFragment())

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.contatos -> replaceFragment(ListContactsFragment())
                R.id.adicionar_contatos -> replaceFragment(RegisterContactsFragment())

                else  -> {

                }


            }
            true
        }
        /*
        setUpListener()
        */

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    /*
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


     */
}