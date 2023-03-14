package com.example.motofix

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import clases.User
import com.google.android.material.navigation.NavigationView
import java.io.*


class MainActivity : AppCompatActivity() {

    var  usuarioLogado:User?=null;

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        val bundle:Bundle?=intent.extras
        if(bundle!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                this.usuarioLogado=bundle.getParcelable("usuarioLogado",User::class.java)
            }else{
                this.usuarioLogado=bundle.getParcelable("usuarioLogado");
            }
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val btn_menu : Button = findViewById<Button>(R.id.btn_menu)
        setSupportActionBar(toolbar)

        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        btn_menu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


        val alertButton: Button = findViewById<Button>(R.id.alert_btn)
        val userButton: Button = findViewById<Button>(R.id.userBtn)
        val preferenceButton: Button = findViewById<Button>(R.id.preferencias_btn)

        preferenceButton.setOnClickListener{
            val i = Intent(this, Preferencias::class.java)
            startActivity(i)
        }


        alertButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_alerta, null)
            builder.setView(dialogView)

            val supportCheckbox = dialogView.findViewById<CheckBox>(R.id.support_checkbox)
            val premiumCheckbox = dialogView.findViewById<CheckBox>(R.id.premium_checkbox)
            val charityCheckbox = dialogView.findViewById<CheckBox>(R.id.charity_checkbox)

            supportCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    premiumCheckbox.isChecked = false
                    charityCheckbox.isChecked = false
                }
            }

            premiumCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    supportCheckbox.isChecked = false
                    charityCheckbox.isChecked = false
                }
            }

            charityCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    supportCheckbox.isChecked = false
                    premiumCheckbox.isChecked = false
                }
            }

            builder.setPositiveButton("Comprar") { _, _ ->
                when {
                    supportCheckbox.isChecked -> Toast.makeText(
                        this,
                        "Has comprado el soporte técnico",
                        Toast.LENGTH_SHORT
                    ).show()
                    premiumCheckbox.isChecked -> Toast.makeText(
                        this,
                        "Has comprado la experiencia premium",
                        Toast.LENGTH_SHORT
                    ).show()
                    charityCheckbox.isChecked -> Toast.makeText(
                        this,
                        "Has hecho una donación caritativa",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }

        val filename = "usuario.txt"
        val file = File(filesDir, filename)
        val text = usuarioLogado.toString()

        val outputStream: FileOutputStream
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE)
            outputStream.write(text.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        userButton.setOnClickListener{
            val filename = "usuario.txt"
            val file = File(filesDir, filename)
            try {
                val reader = BufferedReader(FileReader(file))
                val content = StringBuilder()
                var line = reader.readLine()
                while (line != null) {
                    content.append(line)
                    line = reader.readLine()
                }
                reader.close()
                Toast.makeText(this, content, Toast.LENGTH_LONG).show()

                // Aquí puedes hacer algo con el contenido del archivo, como mostrarlo en un TextView
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }
}