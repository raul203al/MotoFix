package com.example.motofix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import clases.DatePickerFragment
import clases.User
import com.example.motofix.databinding.LayoutRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: LayoutRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButtonRegister.setOnClickListener{
            if (binding.birthdayInput.text.toString() == "" || binding.emailInput.text.toString() == "" || binding.usernameInput.text.toString() == "" || binding.passInput.text.toString() == ""){
                Toast.makeText(this, "Faltan Campos", Toast.LENGTH_LONG).show()
            }else{
                val usuarioLogado =
                    User(binding.usernameInput.text.toString(), binding.emailInput.text.toString(), binding.passInput.text.toString())
                val auth: FirebaseFirestore = FirebaseFirestore.getInstance()
                val docRef = auth.collection("usuarios").document(usuarioLogado.email)
                val data = hashMapOf(
                    "username" to usuarioLogado.username,
                    "email" to usuarioLogado.email,
                    "password" to usuarioLogado.password,
                )
                docRef.set(data)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val i = Intent(this, LoginActivity::class.java)
                            startActivity(i)
                        }else {
                            it.exception?.printStackTrace()
                            Toast.makeText(
                                this,
                                it.exception.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }


            }
        }

        binding.loginButtonRegister.setOnClickListener{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        binding.birthdayInput.setOnClickListener{
            showDatePickerDialog()
        }


    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            binding.birthdayInput.setText(selectedDate)
        }

        newFragment.show(supportFragmentManager, "datePicker")
    }
}