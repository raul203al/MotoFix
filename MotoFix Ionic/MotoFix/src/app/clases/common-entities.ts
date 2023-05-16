export interface User{
    userName: String,
    password: String

}

export interface Problem{
    title: String,
    description: String,
    registration: String,
    model: String,
    owner: String,
    telephone: String,
    price: Number,
    dateEntry: String,
    dateExit: String,
    priority: String,
    images: Uint8Array[]
}
