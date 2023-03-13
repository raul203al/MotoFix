package com.example.motofix

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class Preferencias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)
        val modoSwitch: Switch = findViewById<Switch>(R.id.modo_switch)
        val aplicarBtn: Button = findViewById<Button>(R.id.aplicar_Btn)


        modoSwitch.setOnClickListener {
            if (!modoSwitch.isChecked) {
                modoSwitch.setText("Modo Claro")

            } else {
                modoSwitch.setText("Modo Oscuro")

            }
        }

        aplicarBtn.setOnClickListener {
            if (!modoSwitch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            }

            Toast.makeText(this,"Cambios aplicados", Toast.LENGTH_LONG).show()
        }
    }
}