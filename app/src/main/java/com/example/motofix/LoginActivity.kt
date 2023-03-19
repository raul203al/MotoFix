package com.example.motofix

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import clases.User
import com.example.motofix.databinding.LayoutLoginBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.io.*

class LoginActivity : ActivitySwitch() {
    private lateinit var binding: LayoutLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val preferencias: SharedPreferences = getSharedPreferences(
                "preferenciasPersonalizadas", Context.MODE_PRIVATE
            )
            var lector: BufferedReader? = null
            lector = BufferedReader(InputStreamReader(this.openFileInput("datosLogin.txt")))

            if (lector != null) {
                binding.emailInput.setText(lector.readLine())
                binding.passInput.setText(lector.readLine())
                binding.rcUser.isChecked = true
            }
        } catch (e: FileNotFoundException) {
            Log.d("Archivo datosLogin", "Archivo datosLogin no encontrado")
        }

        binding.loginButtonLogin.setOnClickListener {
            if (!binding.emailInput.text.isNullOrBlank() || !binding.passInput.text.isNullOrBlank()) {

                if (binding.rcUser.isChecked) {
                    var escritor: Writer
                    escritor = OutputStreamWriter(
                        openFileOutput(
                            "datosLogin.txt",
                            Context.MODE_PRIVATE
                        )
                    )

                    escritor.write(binding.emailInput.text.toString() + "\n")
                    escritor.write(binding.passInput.text.toString())
                    escritor.flush()
                    escritor.close()
                } else {
                    deleteFile("datosLogin.txt");

                }

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
                            val loggedUser =
                                User(username!!, email!!, pass!!)
                            this.switchScreen("MainActivity")
                        }

                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()

                    }
                }


            }
        }

        binding.registerButtonLogin.setOnClickListener {
            this.switchScreen("RegisterActivity")
        }
    }
}
