package com.example.registercontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.registercontacts.View.ListContactsFragment
import com.example.registercontacts.View.RegisterContactsFragment
import com.example.registercontacts.databinding.ActivityContactsBinding
import com.example.registercontacts.databinding.ActivityMainBinding

class Contacts : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        replaceFragment(ListContactsFragment())
        binding = ActivityContactsBinding.inflate(layoutInflater)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.contatos -> replaceFragment(ListContactsFragment())
                R.id.adicionar_contatos -> replaceFragment(RegisterContactsFragment())

                else -> {

                }


            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}