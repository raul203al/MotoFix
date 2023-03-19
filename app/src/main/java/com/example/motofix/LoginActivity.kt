package com.example.motofix

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import clases.User
import com.example.motofix.databinding.LoginLayoutBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.io.*

/** Clase LoginActivity que extiende de ActivitySwitch. */
class LoginActivity : ActivitySwitch() {
    /** Objeto que se utiliza para inflar y acceder a los elementos de la vista de diseño. */
    private lateinit var binding: LoginLayoutBinding

    /**
     * Método que se llama cuando se crea la actividad por primera vez.
     * @param savedInstanceState Instancia de Bundle para restaurar el estado anterior de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se intenta leer los datos de inicio de sesión previamente guardados en un archivo llamado "datosLogin.txt".
        try {
            var lector: BufferedReader? = null
            lector = BufferedReader(InputStreamReader(this.openFileInput("datosLogin.txt")))

            // Si se encuentra el archivo, se establecen los valores de nombre de usuario y contraseña en los campos correspondientes.
            if (lector != null) {
                binding.userInput.setText(lector.readLine())
                binding.passInput.setText(lector.readLine())
                binding.rcUser.isChecked = true
            }
        } catch (e: FileNotFoundException) {
            // Si no se encuentra el archivo, se escribe un mensaje en la consola de depuración (logcat).
            Log.d("Archivo datosLogin", "Archivo datosLogin no encontrado")
        }

        // Se establece un OnClickListener para el botón de inicio de sesión.
        binding.loginButtonLogin.setOnClickListener {
            // Si se han ingresado valores en los campos de texto de nombre de usuario y contraseña...
            if (!binding.userInput.text.isNullOrBlank() || !binding.passInput.text.isNullOrBlank()) {

                // Si se ha seleccionado la casilla "Recordar usuario"...
                if (binding.rcUser.isChecked) {
                    var escritor: Writer
                    // Se escribe la información de inicio de sesión en el archivo "datosLogin.txt".
                    escritor = OutputStreamWriter(
                        openFileOutput(
                            "datosLogin.txt",
                            Context.MODE_PRIVATE
                        )
                    )

                    escritor.write(binding.userInput.text.toString() + "\n")
                    escritor.write(binding.passInput.text.toString())
                    escritor.flush()
                    escritor.close()
                } else {
                    // Si no se ha seleccionado, se elimina el archivo "datosLogin.txt".
                    deleteFile("datosLogin.txt");

                }

                // Se establece una conexión con Firestore para verificar si el usuario proporcionado es un usuario registrado.
                val auth: FirebaseFirestore = FirebaseFirestore.getInstance()
                val docRef =
                    auth.collection("users").document(binding.userInput.text.toString())
                docRef.get().addOnSuccessListener { documentSnapshot ->
                    // Si el usuario existe...
                    if (documentSnapshot.exists()) {
                        // Si el documento existe, extraiga los datos del usuario de Firestore y compare la contraseña ingresada con la contraseña almacenada en Firestore.
                        val username = documentSnapshot.getString("user")
                        val email = documentSnapshot.getString("email")
                        val pass = documentSnapshot.getString("password")
                        val bd = documentSnapshot.getString("bd")

                        if (binding.passInput.text.toString().equals(pass)) {
                            // Si las contraseñas coinciden, cree un objeto User y muestre un mensaje de bienvenida en pantalla.
                            loggedUser = User(username!!, email!!, pass!!, bd)
                            binding.userInput.setText("")
                            binding.passInput.setText("")
                            Toast.makeText(
                                this,
                                "Bienvenid@: " + loggedUser!!.username,
                                Toast.LENGTH_LONG
                            ).show()
                            this.switchScreen("MainActivity")
                        } else {
                            // Si las contraseñas no coinciden, muestre un mensaje de error en pantalla.
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }

                    } else {
                        // Si el documento no existe, muestre un mensaje de error en pantalla.
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    }
                }


            }
        }

        // Cuando el botón de registro es presionado, cambia a la actividad de registro.
        binding.registerButtonLogin.setOnClickListener {
            this.switchScreen("RegisterActivity")
        }
    }
}
