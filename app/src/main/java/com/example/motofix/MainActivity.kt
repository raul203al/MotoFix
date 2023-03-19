package com.example.motofix

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.MotorBike
import clases.MotorBikeAdapter
import clases.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
/**

Esta clase representa la actividad principal de la aplicación "Motofix".
Muestra una lista de motocicletas registradas y permite al usuario registrar un problema
y cerrar sesión.
 */
class MainActivity : ActivitySwitch() {

    private val motorBikeList = ArrayList<MotorBike>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var motorBikeAdapter: MotorBikeAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var navigationView: NavigationView

    /**
     * Se llama cuando se crea la actividad. Inicializa los componentes de la interfaz de usuario
     * y carga los datos de las motocicletas registradas en Firebase Firestore.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            loggedUser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("usuarioLogado", User::class.java)
            } else {
                bundle.getParcelable("usuarioLogado")
            }
        }

        recyclerView = findViewById(R.id.recyclerUsuarios)
        motorBikeAdapter = MotorBikeAdapter(this, motorBikeList)
        recyclerView.adapter = motorBikeAdapter
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)

        db = FirebaseFirestore.getInstance()
        db.collection("motos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val id = document.id
                    val owner = document.getString("owner")
                    val problem = document.getString("problem")
                    val description = document.getString("description")
                    val dateEntry = document.getString("dateentry")
                    val dateExit = document.getString("dateexit")
                    val status = document.getString("status")
                    val motorBike = MotorBike(
                        id, owner ?: "", problem ?: "", description ?: "",
                        dateEntry ?: "", dateExit ?: "", status ?: ""
                    )
                    motorBikeList.add(motorBike)
                }
                motorBikeAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        val report = findViewById<Button>(R.id.report)
        val logout = findViewById<Button>(R.id.logout)

        report.setOnClickListener {
            val i = Intent(this, ProblemRegister::class.java)
            startActivity(i)
        }

        logout.setOnClickListener {
            switchScreen("LoginActivity")
        }
    }

    /**
     * Infla el menú de opciones de la actividad.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    /**
     * Maneja las acciones seleccionadas en el menú de opciones.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_report_problem -> {
                // Acción para registrar problema
                val intent = Intent(this, ProblemRegister::class.java)
                startActivity(intent)
                true
            }
            R.id.nav_logout -> {
                // Acción para cerrar sesión
                switchScreen("LoginActivity")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
