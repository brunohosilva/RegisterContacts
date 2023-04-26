package com.example.registercontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.registercontacts.View.*
import com.example.registercontacts.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth


        setUpListener()


    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, Contacts::class.java)
            startActivity(intent)
        }
    }

    private fun setUpListener() {
        binding.txtSignUp.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        })
        binding.btnEntrar.setOnClickListener(View.OnClickListener {

            var email = binding.txtEmail.text.toString()
            var password = binding.txtPassword.text.toString()

            if (password.isNullOrEmpty() || email.isNullOrEmpty()) {
                Toast.makeText(
                    baseContext, "Preencha o e-mail e/ou a senha.",
                    Toast.LENGTH_SHORT
                ).show()

                return@OnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext, "Login realizado com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, Contacts::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Falha na autenticação. ${task.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
    }


}
