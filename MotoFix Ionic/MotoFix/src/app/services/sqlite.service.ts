import { Injectable } from '@angular/core';
import { SQLite, SQLiteObject } from '@ionic-native/sqlite/ngx';
import { User } from '../clases/common-entities';

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
        'CREATE TABLE IF NOT EXISTS problem (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, description VARCHAR, registration VARCHAR, model VACHAR, owner VARCHAR, telephone VARCHAR, price REAL, dateEntry VARCHAR, ,dateExit VARCHAR, priority VARCHAR, image1 BLOB, image2 BLOB, image3 BLOB, image4 BLOB, image5 BLOB)',
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
}

