package clases

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.motofix.R


class MotorBikeAdapter(val actividadMadre: Activity, val datos:ArrayList<MotorBike>) : RecyclerView.Adapter<MotorBikeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotorBikeViewHolder {
        return MotorBikeViewHolder(actividadMadre.layoutInflater.inflate(R.layout.motorbike_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MotorBikeViewHolder, position: Int) {
        val moto:MotorBike = datos.get(position)
        holder.nombreApellidos.text=moto.id
        holder.email.text=moto.problem
        holder.contenedor.setOnClickListener {
            val inflater = LayoutInflater.from(holder.itemView.context)
            val dialogView = inflater.inflate(R.layout.motorbike_details, null)

            val imageView = dialogView.findViewById<ImageView>(R.id.imageView)

            val drawable : Drawable = dialogView.resources.getDrawable(R.drawable.placeholder)
            val bitmap = drawable.toBitmap()
            imageView.setImageBitmap(bitmap)

            val tvOwner = dialogView.findViewById<TextView>(R.id.tv_owner)
            tvOwner.text = "Owner: " + holder.nombreApellidos.text

            val tvProblem = dialogView.findViewById<TextView>(R.id.tv_problem)
            tvProblem.text = "Problem: " + holder.email.text

            AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)
                .show()
        }

    }

    override fun getItemCount(): Int {
        return datos.size
    }}