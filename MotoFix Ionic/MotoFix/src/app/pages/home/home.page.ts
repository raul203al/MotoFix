import { Component } from '@angular/core';
import { IonicModule, ModalController, NavController } from '@ionic/angular';
import { MotoDetailPage } from './moto-detail/moto-detail.page'; // Importa el componente del modal
import { UtilsService } from 'src/app/services/utils.service';
import { Incident, Problem, UserPhoto } from 'src/app/clases/common-entities';
import { SqliteService } from 'src/app/services/sqlite.service';
import { CommonModule } from '@angular/common';
import { PhotoService } from 'src/app/services/photo.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule],
})
export class HomePage {
  incidents: Incident[] = []
  constructor(private photoService: PhotoService, private db: SqliteService, private navController: NavController, private modalController: ModalController, private utils: UtilsService) { }

  async ngOnInit() {
    this.getIncident()
    console.log(this.incidents)
  }

  add() {

  }

  logout() {
    this.utils.navigateReplacingUrl('/login');
  }

  goToIncidents() {
    this.utils.navigateReplacingUrl('/home');
  }

  goToTasks() {
    this.utils.navigateReplacingUrl('/tasks');
  }

  async getIncident() {
    this.incidents = [];
    await this.db.getIncidents().then((data) => {
      console.log(data)
    })
    let problem: Problem[] = await this.db.getIncidents()

    for (let i = 0; i < problem.length; i++) {
      let inc: Incident = {
        id: problem[i].id,
        registration: problem[i].registration,
        model: problem[i].model,
        problem: problem[i].problem,
        description: problem[i].description,
        dateEntry: problem[i].dateEntry,
        dateExit: problem[i].dateExit,
        priority: problem[i].priority,
        image: await this.photoService.convertUint8ArrayToUserPhoto(problem[i].image)
      }
      console.log(inc)
      this.incidents.push(inc)
    }
  }

  async detail(incident: Incident) {
    const modal = await this.modalController.create({
      component: MotoDetailPage, // Componente que deseas mostrar como modal
      cssClass: 'modal-class', // Clase CSS opcional para personalizar el estilo del modal
      componentProps: {
        isNew: false,
        detailMode: true,
        incident
        // Puedes pasar propiedades adicionales al componente del modal si es necesario
      }
    });

    modal.onDidDismiss().then(() => {
      this.getIncident()
      console.log(this.incidents)
      // Aquí puedes realizar acciones después de que se cierra el modal
    });

    return await modal.present();
  }

  async newIncident() {
    const modal = await this.modalController.create({
      component: MotoDetailPage, // Componente que deseas mostrar como modal
      cssClass: 'modal-class', // Clase CSS opcional para personalizar el estilo del modal
      componentProps: {
        isNew: true,
        detailMode: false
        // Puedes pasar propiedades adicionales al componente del modal si es necesario
      }
    });

    modal.onDidDismiss().then(() => {
      this.getIncident()
      console.log(this.incidents)
      // Aquí puedes realizar acciones después de que se cierra el modal
    });

    return await modal.present();
  }

}
