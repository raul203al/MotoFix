package com.example.motofix

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import clases.MotorBike
import com.example.motofix.databinding.ActivityMotoEditBinding
import com.example.motofix.databinding.LoginLayoutBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.util.*
/**

Esta actividad permite la edición de los datos de una moto existente.
Se accede a ella desde la actividad MainActivity al hacer clic en una moto de la lista.
Los datos de la moto seleccionada se muestran en los campos correspondientes para su edición.
Los cambios se aplican al hacer clic en el botón "Aplicar cambios".
 */
class MotoEdit : ActivitySwitch() {

    private lateinit var binding: ActivityMotoEditBinding

    /**
     * Método que se ejecuta al crear la actividad. Se inicializan los elementos de la vista y se obtienen los datos de la moto seleccionada.
     * @param savedInstanceState Instancia guardada del estado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotoEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = this.intent.extras
        var moto: MotorBike? = null
        if (bundle != null) {
            moto = bundle.getParcelable<MotorBike>("moto")
            if (moto != null) {
                binding.idInput.setText(moto.id)
                binding.ownerInput.setText(moto.owner)
                binding.problemInput.setText(moto.problem)
                binding.descriptionInput.setText(moto.description)
                binding.dateEntryInput.setText(moto.dateEntry.toString())
                binding.dateExitInput.setText(moto.dateExit.toString())
                binding.statusInput.setText(moto.status)
            }
        }

        binding.dateEntryInput.setOnClickListener{
            showDatePickerDialog(binding.dateEntryInput)
        }

        binding.dateExitInput.setOnClickListener{
            showDatePickerDialog(binding.dateExitInput)
        }

        binding.botonAplicarCambios.setOnClickListener {
            moto!!.id = binding.idInput.text.toString()
            moto.owner = binding.ownerInput.text.toString()
            moto.problem = binding.problemInput.text.toString()
            moto.description = binding.descriptionInput.text.toString()
            moto.dateEntry = binding.dateEntryInput.text.toString()
            moto.dateExit = binding.dateExitInput.text.toString()
            moto.status = binding.statusInput.text.toString()

            switchScreen("MainActivity")
        }
    }
}
