package clases

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.os.Parcel
import android.os.Parcelable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.motofix.R
import java.time.LocalDate


class MotorBike : Parcelable {
    var id: String?
    var owner: String
    var problem: String
    var description: String
    var dateEntry: LocalDate?
    var dateExit: LocalDate?
    var status: String?

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        owner = parcel.readString()!!
        problem = parcel.readString()!!
        description = parcel.readString()!!
        dateEntry = LocalDate.parse(parcel.readString()!!)
        dateExit = LocalDate.parse(parcel.readString()!!)
        status = parcel.readString()
    }

    constructor(
        id: String,
        owner: String,
        problem: String,
        description: String,
        dateEntry: LocalDate,
        dateExit: LocalDate,
        status: String,
    ) {
        this.id = id
        this.owner = owner
        this.problem = problem
        this.description = description
        this.dateEntry = dateEntry
        this.dateExit = dateExit
        this.status = status
    }
    constructor() {
        this.id = "0"
        this.owner = "Hola"
        this.problem = "Problema"
        this.description = "Descripcion"
        this.dateEntry = LocalDate.now()
        this.dateExit = LocalDate.now()
        this.status = "Si"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(owner)
        parcel.writeString(problem)
        parcel.writeString(description)
        parcel.writeString(dateEntry.toString())
        parcel.writeString(dateExit.toString())
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<MotorBike> {
        override fun createFromParcel(parcel: Parcel): MotorBike {
            return MotorBike(parcel)
        }

        override fun newArray(size: Int): Array<MotorBike?> {
            return arrayOfNulls(size)
        }
    }
}
