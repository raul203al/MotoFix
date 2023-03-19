package clases

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate
import android.graphics.BitmapFactory


class MotorBike : Parcelable {
    var id: String?
    var owner: String
    var problem: String
    var description: String
    var dateEntry: LocalDate?
    var dateExit: LocalDate?
    var status: String?
    var images: ArrayList<Bitmap>?

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        owner = parcel.readString()!!
        problem = parcel.readString()!!
        description = parcel.readString()!!
        dateEntry = LocalDate.parse(parcel.readString()!!)
        dateExit = LocalDate.parse(parcel.readString()!!)
        status = parcel.readString()
        val size = parcel.readInt()
        for (i in 0 until size) {
            val byteArray = parcel.createByteArray()
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
            images?.add(bitmap)
        }
    }

    constructor(
        id: String,
        owner: String,
        problem: String,
        description: String,
        dateEntry: LocalDate,
        dateExit: LocalDate,
        status: String,
        images: ArrayList<Bitmap>
    ) {
        this.id = id
        this.owner = owner
        this.problem = problem
        this.description = description
        this.dateEntry = dateEntry
        this.dateExit = dateExit
        this.status = status
        this.images = images
    }

    constructor() {
        this.id = null
        this.owner = null!!
        this.problem = null!!
        this.description = null!!
        this.dateEntry = null
        this.dateExit = null
        this.status = null
        this.images = null
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(owner)
        parcel.writeString(problem)
        parcel.writeString(description)
        parcel.writeString(dateEntry.toString())
        parcel.writeString(dateExit.toString())
        parcel.writeString(status)

        parcel.writeInt(images!!.size) // Escribir la cantidad de elementos en el ArrayList
        for (image in images!!) {
            image.writeToParcel(parcel, flags)
        }
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
