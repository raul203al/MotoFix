package clases

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate


/**
 * Clase que representa una moto y su información asociada.
 */
class MotorBike : Parcelable {
    var id: String? // El ID de la moto.
    var owner: String // El propietario de la moto.
    var problem: String // El problema reportado de la moto.
    var description: String // Descripción de la moto.
    var dateEntry: String? // Fecha de entrada de la moto en el taller.
    var dateExit: String? // Fecha de salida de la moto del taller.
    var status: String? // Estado de la moto (en reparación, reparado, etc).

    /**
     * Constructor primario utilizado al crear una instancia de la clase MotorBike a través de un objeto Parcelable.
     */
    constructor(parcel: Parcel) : this() {
        // Lee los valores del parcel y los asigna a las propiedades correspondientes.
        id = parcel.readString()
        owner = parcel.readString()!!
        problem = parcel.readString()!!
        description = parcel.readString()!!
        dateEntry = parcel.readString()
        dateExit = parcel.readString()
        status = parcel.readString()
    }

    /**
     * Constructor secundario utilizado para crear una nueva instancia de la clase MotorBike.
     */
    constructor(
        id: String,
        owner: String,
        problem: String,
        description: String,
        dateEntry: String,
        dateExit: String?,
        status: String,
    ) {
        // Asigna los valores recibidos como parámetros a las propiedades correspondientes.
        this.id = id
        this.owner = owner
        this.problem = problem
        this.description = description
        this.dateEntry = dateEntry
        this.dateExit = dateExit
        this.status = status
    }

    /**
     * Constructor secundario utilizado para crear una nueva instancia de la clase MotorBike con valores predeterminados.
     */
    constructor() {
        // Asigna valores predeterminados a las propiedades de la clase.
        this.id = "0"
        this.owner = "Hola"
        this.problem = "Problema"
        this.description = "Descripcion"
        this.dateEntry = LocalDate.now().toString()
        this.dateExit = LocalDate.now().toString()
        this.status = "Si"
    }

    /**
     * Método utilizado para escribir los valores de las propiedades de la clase en un Parcel.
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(owner)
        parcel.writeString(problem)
        parcel.writeString(description)
        parcel.writeString(dateEntry.toString())
        parcel.writeString(dateExit.toString())
        parcel.writeString(status)
    }

    /**
     * Método utilizado para obtener información adicional sobre el objeto Parcelable.
     */
    override fun describeContents(): Int {
        return 0
    }

    /**
     * Companion object que proporciona un objeto CREATOR para crear instancias de la clase MotorBike a partir de un Parcel.
     */
    companion object CREATOR : Parcelable.Creator<MotorBike> {
        /**
         * Método para crear una nueva instancia de la clase MotorBike a partir de un Parcel.
         */
        override fun createFromParcel(parcel: Parcel): MotorBike {
            return MotorBike(parcel)
        }

        /**
         * Método para crear un array de instancias de la clase MotorBike.
         */
        override fun newArray(size: Int): Array<MotorBike?> {
            return arrayOfNulls(size)
        }
    }
}
