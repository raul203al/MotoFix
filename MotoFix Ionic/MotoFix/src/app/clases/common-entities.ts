export interface User {
    userName: String,
    password: String

}

export interface Problem {
    id: number,

    registration: String,
    model: String,

    problem: String,
    description: String,
    
    dateEntry: String,
    dateExit: String,

    priority: String,
    image: Uint8Array
}

export interface Incident {
    id: number,


    registration: String,
    model: String,

    problem: String,
    description: String,
    
    dateEntry: String,
    dateExit: String,

    priority: String,
    image: UserPhoto
}


export interface UserPhoto {
    filepath: string;
    webviewPath?: string;
}

export interface Tarea{
    id: number,
    task: String,
    isChecked: number
}