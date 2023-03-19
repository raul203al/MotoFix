package clases

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.motofix.R
import java.time.LocalDate

class MotorBikeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imagenPerfil: ImageView by lazy{ view.findViewById<ImageView>(R.id.imagenPerfil) }
    val nombreApellidos: TextView by lazy{ view.findViewById<TextView>(R.id.nombreApellidos)}
    val email: TextView by lazy{ view.findViewById<TextView>(R.id.email) }
    val contenedor: LinearLayout by lazy{ view.findViewById<LinearLayout>(R.id.contenedorElemento) }


    /*var id: String?
    var owner: String
    var problem: String
    var description: String
    var dateEntry: LocalDate?
    var dateExit: LocalDate?
    var status: String?*/
}