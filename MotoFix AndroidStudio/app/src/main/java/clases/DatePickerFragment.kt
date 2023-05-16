package clases

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import java.util.*


/**
 * Clase que muestra un cuadro de diálogo para seleccionar una fecha utilizando el widget "DatePicker" de Android.
 */
class DatePickerFragment : DialogFragment() {

    /**
     * Variable listener que será utilizada para notificar la fecha seleccionada.
     */
    private var listener: OnDateSetListener? = null

    /**
     * Método para establecer el listener.
     * @param listener El listener que se utilizará para notificar la fecha seleccionada.
     */
    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    /**
     * Método para crear y devolver un diálogo DatePickerDialog.
     * @param savedInstanceState El estado guardado anteriormente de la actividad.
     * @return Un nuevo diálogo DatePickerDialog con la fecha actual y el listener establecido previamente.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Crea una instancia de Calendar con la fecha actual.
        val c = Calendar.getInstance()
        // Obtiene el año, mes y día de la instancia de Calendar.
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        // Crea y devuelve un nuevo DatePickerDialog con la fecha actual y el listener establecido previamente.
        return DatePickerDialog(requireActivity(), listener, year, month, day)
    }

    /**
     * Companion object que proporciona un método estático newInstance que crea y devuelve una nueva instancia de DatePickerFragment.
     */
    companion object {
        /**
         * Método para crear y devolver una nueva instancia de DatePickerFragment.
         * @param listener El listener que se utilizará para notificar la fecha seleccionada.
         * @return Una nueva instancia de DatePickerFragment con el listener establecido previamente.
         */
        fun newInstance(listener: OnDateSetListener?): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.setListener(listener)
            return fragment
        }
    }
}
