package com.example.motofix

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import clases.User

open abstract class ActivitySwitch : AppCompatActivity() {
    var  loggedUser:User?=null

    public fun switchScreen(nombreActividad:String):Unit{
        val intent: Intent = Intent(this,
            Class.forName("com.example.motofix."+nombreActividad))
        val bundle:Bundle=Bundle()
        bundle.putParcelable("usuarioLogado",loggedUser);
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle:Bundle?=intent.extras
        if(bundle!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                this.loggedUser=bundle.getParcelable("usuarioLogado",User::class.java)
            }else{
                this.loggedUser=bundle.getParcelable("usuarioLogado");
            }

        }
    }
}