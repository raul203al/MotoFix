package com.example.motofix

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import clases.DatePickerFragment
import clases.User
import com.google.android.material.textfield.TextInputEditText

/**
 * Clase abstracta utilizada como base para las actividades de la aplicación.
 * Proporciona una función para cambiar de actividad y una función para mostrar un diálogo de selección de fecha.
 * También almacena el usuario que ha iniciado sesión en la actividad.
 */
open abstract class ActivitySwitch : AppCompatActivity() {
    var loggedUser: User? = null

    /**
     * Función utilizada para cambiar de actividad.
     * @param nombreActividad Nombre de la actividad a la que se quiere cambiar.
     */
    public fun switchScreen(nombreActividad: String): Unit {
        val intent: Intent = Intent(
            this,
            Class.forName("com.example.motofix.$nombreActividad")
        )
        val bundle: Bundle = Bundle()
        bundle.putParcelable("usuarioLogado", loggedUser);
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            loggedUser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("usuarioLogado", User::class.java)
            } else {
                bundle.getParcelable("usuarioLogado")
            }
        }
    }

    /**
     * Función utilizada para mostrar un diálogo de selección de fecha.
     * @param input Campo de texto en el que se quiere mostrar la fecha seleccionada.
     */
    public fun showDatePickerDialog(input: TextInputEditText) {
        val newFragment = DatePickerFragment.newInstance { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = "$day / ${month + 1} / $year"
            input.setText(selectedDate)
        }

        newFragment.show(supportFragmentManager, "datePicker")
    }
}
