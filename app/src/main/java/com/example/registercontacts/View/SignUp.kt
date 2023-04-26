package com.example.registercontacts.View

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.registercontacts.MainActivity
import com.example.registercontacts.R
import com.example.registercontacts.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        setUpListener()

    }

    private fun setUpListener() {
        binding.btnEntrar.setOnClickListener(View.OnClickListener {

            var email = binding.txtEmail.text.toString().trim()
            var password = binding.txtPassword.text.toString()
            var confirmPassword = binding.txtPassword2.text.toString()

            if (email.isNullOrEmpty() || password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()) {
                Toast.makeText(
                    baseContext, "Todos os campos são obrigatórios.",
                    Toast.LENGTH_SHORT
                ).show()

                return@OnClickListener
            }

            if(password.length < 6) {
                Toast.makeText(
                    baseContext, "A senha deve conter pelo menos 6 caracteres.",
                    Toast.LENGTH_SHORT
                ).show()

                return@OnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(
                    baseContext, "As senhas devem ser iguais.",
                    Toast.LENGTH_SHORT
                ).show()

                return@OnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Log.d(TAG, user.toString())
                        Toast.makeText(
                            baseContext, "Usuário cadastrado com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Erro ao cadastrar usuário. ${task.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
    }
}