import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { UtilsService } from 'src/app/services/utils.service';
import Swal from 'sweetalert2';
import { SqliteService } from 'src/app/services/sqlite.service';
import { Tarea } from 'src/app/clases/common-entities';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.page.html',
  styleUrls: ['./tasks.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule]
})
export class TasksPage implements OnInit {
  tasks: Tarea[] = [];

  constructor(private utils: UtilsService, private db: SqliteService) { }

  ngOnInit() {
    this.getTasks();
  }

  logout() {
    this.utils.navigateReplacingUrl('login')
  }

  goToTasks() {
    this.utils.navigateReplacingUrl('tasks')

  }

  goToIncidents() {
    this.utils.navigateReplacingUrl('incidents')

  }

  async getTasks() {
    this.tasks = [];
    this.tasks = await this.db.getTasks()
    console.log(this.tasks)
  }

  newTask() {
    Swal.fire({
      title: "<i>Añadir Tarea</i>",
      html: "<ion-input id='taskInput' placeholder='Introduzca una Tarea'></ion-input>",
      confirmButtonText: 'Aceptar',
      cancelButtonText: 'Cancelar'
    }).then(async (result) => {
      if (result.isConfirmed) {
        const taskInput = document.getElementById('taskInput') as HTMLIonInputElement;
        const taskValue = taskInput.value;
        try {
          await this.db.newTask(taskValue!.toString(), false);
          Swal.fire({
            text: 'Se ha añadido la tarea sin problemas',
            icon: 'success'
          })
          await this.getTasks()
          console.log(this.tasks)
        } catch (error) {
          Swal.fire({
            title: 'error',
            text: 'No se ha podido añadir la tarea',
            icon: 'error'
          })
        }
        console.log("Tarea ingresada:", taskValue);
      }
    });
  }

  editTask() {

  }

  updateTask(task: Tarea){
    if (task.isChecked == 0){
      task.isChecked = 1;
    }else{
      task.isChecked = 0;
    }
    console.log(task.isChecked)
    try {
      this.db.updateTask(task.task, task.isChecked, task.id);
      console.log('act')
    } catch (error) {
      
    }
  }

  deleteTask() {

  }

}
