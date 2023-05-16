package clases

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.motofix.MotoEdit
import com.example.motofix.R

/**
 * Adapter para la lista de motocicletas. Extiende RecyclerView.Adapter<MotorBikeViewHolder>
 * @param actividadMadre la actividad que contiene el RecyclerView
 * @param datos la lista de motocicletas
 */
class MotorBikeAdapter(val actividadMadre: Activity, val datos:ArrayList<MotorBike>) : RecyclerView.Adapter<MotorBikeViewHolder>() {

    /**
     * Crea un nuevo ViewHolder para la vista.
     * @param parent el ViewGroup al que se añade la vista
     * @param viewType el tipo de vista que se va a crear
     * @return el ViewHolder creado
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotorBikeViewHolder {
        return MotorBikeViewHolder(actividadMadre.layoutInflater.inflate(R.layout.motorbike_layout,parent,false))
    }

    /**
     * Asocia los datos de una moto a un ViewHolder.
     * @param holder el ViewHolder que se va a actualizar
     * @param position la posición del elemento en la lista
     */
    @SuppressLint("MissingInflatedId")
    override fun onBindViewHolder(holder: MotorBikeViewHolder, position: Int) {
        val moto:MotorBike = datos.get(position)
        holder.id.text=moto.id
        holder.owner.text=moto.owner
        holder.problem.text=moto.problem
        holder.description.text=moto.description
        holder.dateEntry.text=moto.dateEntry.toString()
        holder.dateExit.text=moto.dateExit.toString()
        holder.status.text=moto.status
        holder.contenedor.setOnClickListener {
            val inflater = LayoutInflater.from(holder.itemView.context)
            val dialogView = inflater.inflate(R.layout.motorbike_details, null)

            val alertDialog = AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)
                .show()

            val imageView = dialogView.findViewById<ImageView>(R.id.imageView)
            val drawable : Drawable = dialogView.resources.getDrawable(R.drawable.placeholder)
            val bitmap = drawable.toBitmap()
            imageView.setImageBitmap(bitmap)

            val tvId = dialogView.findViewById<TextView>(R.id.tv_id)
            tvId.text = "Matricula: " + holder.id.text

            val tvOwner = dialogView.findViewById<TextView>(R.id.tv_owner)
            tvOwner.text = "Dueno: " + holder.owner.text

            val tvProblem = dialogView.findViewById<TextView>(R.id.tv_problem)
            tvProblem.text = "Problema: " + holder.problem.text

            val tvDescription = dialogView.findViewById<TextView>(R.id.tv_description)
            tvDescription.text = "Descripcion: " + holder.description.text

            val tvDateEntry = dialogView.findViewById<TextView>(R.id.tv_date_entry)
            tvDateEntry.text = "Fecha Entrada: " + holder.dateEntry.text

            val tvDateExit = dialogView.findViewById<TextView>(R.id.tv_date_exit)
            tvDateExit.text = "Fecha Salida: " + holder.dateExit.text

            val tvStatus = dialogView.findViewById<TextView>(R.id.tv_status)
            tvStatus.text = "Estado: " + holder.status.text

            val deleteBtn = dialogView.findViewById<Button>(R.id.delete_Btn)
            deleteBtn.setOnClickListener {
                datos.removeAt(position)
                this.notifyDataSetChanged()
                alertDialog.dismiss()
            }

            val editBtn = dialogView.findViewById<Button>(R.id.edit_Btn)
            editBtn.setOnClickListener{
                val intent: Intent = Intent(actividadMadre,MotoEdit::class.java)
                val bundle: Bundle = Bundle()
                bundle.putParcelable("moto",moto)
                intent.putExtras(bundle)
                actividadMadre.startActivity(intent)
            }
        }
    }
    /**
     * Devuelve el número de elementos en la lista de datos
     */
    override fun getItemCount(): Int {
        return datos.size
    }

    /**
     * Actualiza la lista de datos con nuevos datos y notifica al adaptador del cambio
     * @param newData los nuevos datos para actualizar la lista
     */
    fun updateData(newData: ArrayList<MotorBike>) {
        datos.clear()
        datos.addAll(newData)
        notifyDataSetChanged()
    }

}


