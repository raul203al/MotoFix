package com.example.motofix

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import clases.DatePickerFragment
import com.example.motofix.databinding.LayoutLoginBinding
import com.example.motofix.databinding.LayoutRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: LayoutRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButtonRegister.setOnClickListener{
            val i : Intent = Intent(this, LoginActivity::class.java)
            startActivity(i)
            //TODO Guardar Usuario
        }

        binding.loginButtonRegister.setOnClickListener{
            val i : Intent = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        binding.birthdayInput.setOnClickListener{
            showDatePickerDialog()
        }


    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            binding.birthdayInput.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }
}