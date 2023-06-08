import { Injectable } from '@angular/core';
import { SQLite, SQLiteObject } from '@ionic-native/sqlite/ngx';
import { Problem, Tarea, User } from '../clases/common-entities';

@Injectable({
  providedIn: 'root'
})
export class SqliteService {

  database!: SQLiteObject;

  constructor(private db: SQLite) {
    this.initDatabase();
  }

  async initDatabase(): Promise<void> {
    try {
      this.database = await this.db.create({
        name: 'database.db',
        location: 'default',
      });
      await this.createTables();
    } catch (error: any) {
      console.log('Error al abrir la base de datos', error);
    }
  }

  async createTables(): Promise<void> {
    try {
      await this.database.executeSql(
        'CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, password VARCHAR)',
        []
      );
      await this.database.executeSql(
        'CREATE TABLE IF NOT EXISTS incidents (id INTEGER PRIMARY KEY AUTOINCREMENT,registration VARCHAR, model VACHAR, problem VARCHAR, description VARCHAR, dateEntry VARCHAR, dateExit VARCHAR, priority VARCHAR, image BLOB)',
        []
      );
      await this.database.executeSql(
        'CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, task VARCHAR, isChecked INTEGER)',
        []
      );

    } catch (error) {
      console.log('Error al crear las tablas', error);
    }
  }

  async addUser(username: String, password: String): Promise<void> {
    try {
      const db: SQLiteObject = await this.db.create({
        name: 'database.db',
        location: 'default',
      });

      await db.executeSql(
        'INSERT INTO users (username, password) VALUES (?, ?)',
        [username, password]
      );
    } catch (error) {
      console.error('Error al insertar el usuario en la base de datos', error);
    }
  }

  async getUser(username: String, password: String): Promise<User[]> {
    return new Promise((resolve, reject) => {

      this.database.executeSql('SELECT username, password FROM users WHERE username=? AND password=?', [username, password]).then((data) => {
        let dataArray = [];
        for (let i = 0; i < data.rows.length; i++) {
          dataArray.push(data.rows.item(i));
        }
        resolve(dataArray);
      }, (error) => {
        reject(error);
      });
    })
  }

  async getUsers(): Promise<User[]> {
    return new Promise((resolve, reject) => {

      this.database.executeSql('SELECT username, password FROM users', []).then((data) => {
        let dataArray = [];
        for (let i = 0; i < data.rows.length; i++) {
          dataArray.push(data.rows.item(i));
        }
        resolve(dataArray);
      }, (error) => {
        reject(error);
      });
    })
  }


  async newIncident(registration: String, model: String, problem: String, description: String, dateEntry: String, dateExit: String, priority: String, image: Uint8Array): Promise<void> {
    try {
      const db: SQLiteObject = await this.db.create({
        name: 'database.db',
        location: 'default',
      });

      await db.executeSql(
        'INSERT INTO incidents (registration, model, problem, description, dateEntry, dateExit, priority, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)',
        [registration, model, problem, description, dateEntry, dateExit, priority, image]
      )
    } catch (error) {
      console.error('Error al insertar el usuario en la base de datos', error);
    }
  }

  getIncidents(): Promise<Problem[]> {
    return new Promise((resolve, reject) => {

      this.database.executeSql('SELECT * FROM incidents', []).then((data) => {
        let dataArray = [];
        for (let i = 0; i < data.rows.length; i++) {
          dataArray.push(data.rows.item(i));
        }
        resolve(dataArray);
      }, (error) => {
        reject(error);
      });
    })
  }

  updateIncident(registration: String, model: String, problem: String, description: String, dateEntry: String, dateExit: String, priority: String, image: Uint8Array, id: number): void {

    this.database.executeSql('UPDATE incidents SET registration = ?, model = ?, problem = ?, description = ?, dateEntry = ?, dateExit = ?, priority = ?, image = ? WHERE id = ?',
      [registration, model, problem, description, dateEntry, dateExit, priority, image, id]
    ).then((data) => {
      let dataArray = [];
      for (let i = 0; i < data.rows.length; i++) {
        dataArray.push(data.rows.item(i));
      }

    })
  }

  deleteIncident(id: number): void {

    this.database.executeSql('DELETE FROM incidents WHERE id=?', [id]).then((data) => {
      let dataArray = [];
      for (let i = 0; i < data.rows.length; i++) {
        dataArray.push(data.rows.item(i));
      }

    })
  }


  async newTask(task: String, isChecked: Boolean): Promise<void> {
    try {
      const db: SQLiteObject = await this.db.create({
        name: 'database.db',
        location: 'default',
      });

      await db.executeSql(
        'INSERT INTO tasks (task, isChecked) VALUES (?, ?)',
        [task, isChecked]
      )
    } catch (error) {
      console.error('Error al insertar el usuario en la base de datos', error);
    }
  }

  getTasks(): Promise<Tarea[]> {
    return new Promise((resolve, reject) => {

      this.database.executeSql('SELECT * FROM tasks', []).then((data) => {
        let dataArray = [];
        for (let i = 0; i < data.rows.length; i++) {
          dataArray.push(data.rows.item(i));
        }
        resolve(dataArray);
      }, (error) => {
        reject(error);
      });
    })
  }

  updateTask(task: String, isChecked: number, id: number): void {

    this.database.executeSql('UPDATE tasks SET task = ?, isChecked = ? WHERE id = ?',
      [task, isChecked ,id]
    ).then((data) => {
      let dataArray = [];
      for (let i = 0; i < data.rows.length; i++) {
        dataArray.push(data.rows.item(i));
      }

    })
  }

  deleteTask(id: number): void {

    this.database.executeSql('DELETE FROM tasks WHERE id=?', [id]).then((data) => {
      let dataArray = [];
      for (let i = 0; i < data.rows.length; i++) {
        dataArray.push(data.rows.item(i));
      }

    })
  }

}