package com.example.motofix

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import clases.MotorBike
import clases.MotorBikeAdapter
import clases.User


class MainActivity : ActivitySwitch() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        val bundle:Bundle?=intent.extras
        if(bundle!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                this.loggedUser=bundle.getParcelable("usuarioLogado",User::class.java)
            }else{
                this.loggedUser=bundle.getParcelable("usuarioLogado");
            }
        }

        Toast.makeText(this, "Hola " + loggedUser?.username, Toast.LENGTH_LONG).show()

        val valores = arrayListOf<MotorBike>()
        for (i in 100 downTo 1) {
            var moto: MotorBike = MotorBike()
            valores.add(MotorBike())
        }
        val recyclerView: RecyclerView =findViewById<RecyclerView>(R.id.recyclerUsuarios)
        val miAdapter:MotorBikeAdapter=MotorBikeAdapter(this,valores);
        recyclerView.adapter=miAdapter
        val staggeredManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(3,
            StaggeredGridLayoutManager.HORIZONTAL)
        staggeredManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager=staggeredManager


    }
}