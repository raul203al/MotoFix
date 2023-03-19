package com.example.motofix

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import clases.User
import com.example.motofix.databinding.LayoutRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore
/**

Esta clase representa la actividad de registro de usuario en la aplicación. Extiende la clase ActivitySwitch, que proporciona métodos para cambiar entre actividades.
@property binding la instancia de LayoutRegisterBinding que permite acceder a los elementos de la interfaz de usuario definidos en layout_register.xml.
@property loggedUser el usuario que se está registrando. Es un objeto de la clase User, que representa un usuario de la aplicación.
 */
class RegisterActivity : ActivitySwitch() {
    private lateinit var binding: LayoutRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Se define el comportamiento que se llevará a cabo al hacer clic en el botón de registro. Se comprueba que todos los campos obligatorios estén completos. Si no lo están, se muestra un mensaje de error al usuario. Si están completos, se crea una nueva instancia de la clase User con los datos introducidos por el usuario y se guarda en la base de datos Firestore. Si el registro es exitoso, se cambia a la actividad LoginActivity.
         */
        binding.registerButtonRegister.setOnClickListener{
            if (binding.birthdayInput.text.toString() == "" || binding.emailInput.text.toString() == "" || binding.usernameInput.text.toString() == "" || binding.passInput.text.toString() == ""){
                Toast.makeText(this, "Faltan Campos", Toast.LENGTH_LONG).show()
            }else{
                loggedUser =
                    User(binding.usernameInput.text.toString(), binding.emailInput.text.toString(), binding.passInput.text.toString())
                Toast.makeText(this, loggedUser!!.bd, Toast.LENGTH_LONG)
                val auth: FirebaseFirestore = FirebaseFirestore.getInstance()
                val docRef = auth.collection("users").document(loggedUser!!.email)
                val data = hashMapOf(
                    "user" to loggedUser!!.username,
                    "email" to loggedUser!!.email,
                    "password" to loggedUser!!.password,
                    "bd" to loggedUser!!.bd.toString()
                )
                docRef.set(data)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            switchScreen("LoginActivity")
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

        /**
         * Se define el comportamiento que se llevará a cabo al hacer clic en el botón de inicio de sesión. Se cambia a la actividad LoginActivity.
         */
        binding.loginButtonRegister.setOnClickListener{
            switchScreen("LoginActivity")
        }

        /**
         * Se define el comportamiento que se llevará a cabo al hacer clic en el campo de fecha de nacimiento. Se muestra un diálogo de selección de fecha al usuario.
         */
        binding.birthdayInput.setOnClickListener{
            showDatePickerDialog(binding.birthdayInput)
        }


    }



}