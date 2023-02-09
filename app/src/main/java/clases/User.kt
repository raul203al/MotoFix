package clases

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class User : Parcelable {

    var name:String?
    var surname:String?
    var password: String
    var email:String
    var birthdate: LocalDate?
    var isAdmin: Boolean = false

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        surname = parcel.readString()
        password = parcel.readString()!!
        email = parcel.readString()!!
        isAdmin = parcel.readByte() != 0.toByte()
    }

    constructor(name: String, surname: String, email: String, password: String, bd: LocalDate, isAdmin: Boolean = false) : this(){
        this.name=name
        this.surname=surname
        this.password = password
        this.email=email
        this.birthdate = bd
        this.isAdmin = isAdmin
    }

    constructor(){
        this.name= null
        this.surname= null
        this.password = null.toString()
        this.email= null.toString()
        this.birthdate = null
    }

    override fun toString(): String {
        return name + " " + surname
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(password)
        parcel.writeString(email)
        parcel.writeByte(if (isAdmin) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}