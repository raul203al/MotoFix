package com.example.motofix

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import clases.User
import com.example.motofix.databinding.LayoutLoginBinding
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LayoutLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButtonLogin.setOnClickListener {
            if (!binding.emailInput.text.isNullOrBlank() || !binding.passInput.text.isNullOrBlank()) {
                val auth: FirebaseFirestore = FirebaseFirestore.getInstance()
                val docRef =
                    auth.collection("usuarios").document(binding.emailInput.text.toString())
                docRef.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        //TODO: Recuperar de firestore los demás datos del usuario (nombre,apellidos,isAdmin) para
                        //el objeto usuario con todos sus datos, lo suyo es que en firestore la PK del documento (id)
                        //sea el email para ser fácilmente distinguible.
                        val username = documentSnapshot.getString("username")
                        val email = documentSnapshot.getString("email")
                        val pass = documentSnapshot.getString("password")
                        if (binding.passInput.text.toString().equals(pass)) {
                            val usuarioLogado =
                                User(username!!, email!!, pass!!)
                            val i = Intent(this, MainActivity::class.java)
                            val bundle:Bundle=Bundle()
                            bundle.putParcelable("usuarioLogado",usuarioLogado);
                            i.putExtras(bundle)
                            startActivity(i)
                        }

                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()

                    }
                    //TODO Sincronizar con base de datos antes de mandar a la actividad principal

                }


            }
        }

        binding.registerButtonLogin.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }
    }
}
