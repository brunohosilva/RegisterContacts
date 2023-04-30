package com.example.registercontacts.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.registercontacts.R
import com.example.registercontacts.databinding.ActivityContactsBinding

class Contacts : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragment(ListContactsFragment())
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.contatos -> replaceFragment(ListContactsFragment())
                R.id.adicionar_contatos -> replaceFragment(RegisterContactsFragment())
                else -> {}
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