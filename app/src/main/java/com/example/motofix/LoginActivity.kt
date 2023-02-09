package com.example.motofix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.motofix.databinding.LayoutLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LayoutLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButtonLogin.setOnClickListener{
            //TODO Sincronizar con base de datos antes de mandar a la actividad principal
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        binding.registerButtonLogin.setOnClickListener{
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }
    }
}