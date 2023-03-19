package com.example.motofix

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import clases.MotorBike
import clases.User
import com.example.motofix.databinding.LayoutRegisterBinding
import com.example.motofix.databinding.ProblemLayoutBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Actividad para registrar un problema con una moto en la base de datos.
 */
class ProblemRegister : ActivitySwitch() {
    private lateinit var binding: ProblemLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProblemLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el botón para registrar el problema
        binding.problemBtn.setOnClickListener{
            if (binding.problemIdInput.text.isNullOrBlank() || binding.problemOwnerInput.text.isNullOrBlank() || binding.problemInput.text.isNullOrBlank() || binding.problemDescriptionInput.text.isNullOrBlank() ||
                binding.problemDateEntryInput.text.isNullOrBlank() || binding.problemDateExitInput.text.isNullOrBlank()){
                Toast.makeText(this, "Faltan Campos", Toast.LENGTH_LONG).show()
            }else{
                val id = binding.problemIdInput.text
                val owner = binding.problemOwnerInput.text
                val problem = binding.problemInput.text.toString()
                val description = binding.problemDescriptionInput.text
                val dateEntry = binding.problemDateEntryInput.text
                val dateExit = binding.problemDateExitInput.text
                var moto =
                    MotorBike(id.toString(), owner.toString(), problem,
                        description.toString(), dateEntry.toString(), dateExit.toString(), "En Proceso")

                // Guardar el objeto moto en Firestore
                val auth: FirebaseFirestore = FirebaseFirestore.getInstance()
                val docRef = auth.collection("motos").document(moto.id!!)
                val data = hashMapOf(
                    "id" to moto.id,
                    "owner" to moto.owner,
                    "problem" to moto.problem,
                    "description" to moto.description,
                    "dateentry" to moto.dateEntry,
                    "dateexit" to moto.dateExit,
                    "status" to moto.status
                )
                docRef.set(data)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            switchScreen("MainActivity")
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

        // Configurar el botón para volver a la pantalla principal
        binding.backBtn.setOnClickListener {
            switchScreen("MainActivity")
        }

        // Configurar el botón para mostrar el diálogo de selección de fecha de entrada
        binding.problemDateEntryInput.setOnClickListener{
            showDatePickerDialog(binding.problemDateEntryInput)
        }

        // Configurar el botón para mostrar el diálogo de selección de fecha de salida
        binding.problemDateExitInput.setOnClickListener{
            showDatePickerDialog(binding.problemDateExitInput)
        }


    }


}