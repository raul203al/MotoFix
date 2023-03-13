package clases

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class User : Parcelable {

    var username:String?
    var password: String
    var email:String
    var birthdate: LocalDate?

    constructor(parcel: Parcel) : this() {
        username = parcel.readString()
        password = parcel.readString()!!
        email = parcel.readString()!!
    }

    constructor(username : String, email: String, password: String, bd: LocalDate) : this(){
        this.username=username
        this.password = password
        this.email=email
        this.birthdate = bd
    }

    constructor(username : String, email: String, password: String) : this(){
        this.username=username
        this.password = password
        this.email=email
    }

    constructor(){
        this.username= null
        this.password = null.toString()
        this.email= null.toString()
        this.birthdate = null
    }

    override fun toString(): String {
        return "Usuario:" +
                "\t" + username + "\n" + "\t" + email + "\n" + "\t" + password
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(email)
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