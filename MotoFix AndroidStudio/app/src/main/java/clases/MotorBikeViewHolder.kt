package clases

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.motofix.R

/**

Clase que define el ViewHolder para la lista de motocicletas. Extiende RecyclerView.ViewHolder
@param view la vista que contiene los elementos del ViewHolder
 */
class MotorBikeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imagenPerfil: ImageView by lazy { view.findViewById<ImageView>(R.id.imagenPerfil) }
    val id: TextView by lazy { view.findViewById<TextView>(R.id.id) }
    val owner: TextView by lazy { view.findViewById<TextView>(R.id.owner) }
    val problem: TextView by lazy { view.findViewById<TextView>(R.id.problem) }
    val description: TextView by lazy { view.findViewById<TextView>(R.id.description) }
    val dateEntry: TextView by lazy { view.findViewById<TextView>(R.id.dateEntry) }
    val dateExit: TextView by lazy { view.findViewById<TextView>(R.id.dateExit) }
    val status: TextView by lazy { view.findViewById<TextView>(R.id.status) }
    val contenedor: LinearLayout by lazy { view.findViewById<LinearLayout>(R.id.contenedorElemento) }

}