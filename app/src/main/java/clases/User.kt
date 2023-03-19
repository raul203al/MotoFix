package clases

import android.os.Parcel
import android.os.Parcelable

/**
 * Clase para representar un usuario.
 *
 * @property username El nombre de usuario.
 * @property password La contraseña del usuario.
 * @property email El correo electrónico del usuario.
 * @property bd La fecha de nacimiento del usuario.
 */
class User : Parcelable {

    var username:String?
    var password: String
    var email:String
    var bd: String?

    constructor(parcel: Parcel) : this() {
        username = parcel.readString()
        password = parcel.readString()!!
        email = parcel.readString()!!
    }

    constructor(username: String, email: String, password: String, bd: String?) : this(){
        this.username=username
        this.password = password
        this.email=email
        this.bd = bd
    }

    constructor(username : String, email: String, password: String) : this(){
        this.username=username
        this.password = password
        this.email=email
    }

    /**
     * Constructor vacío.
     */
    constructor(){
        this.username= null
        this.password = null.toString()
        this.email= null.toString()
        this.bd = null
    }

    override fun toString(): String {
        return "Usuario:" + username
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(email)
        parcel.writeString(bd.toString())
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
