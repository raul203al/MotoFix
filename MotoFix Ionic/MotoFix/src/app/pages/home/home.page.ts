import { Component } from '@angular/core';
import { IonicModule, ModalController, NavController } from '@ionic/angular';
import { MotoDetailPage } from './moto-detail/moto-detail.page'; // Importa el componente del modal

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: true,
  imports: [IonicModule],
})
export class HomePage {
  constructor(private navController: NavController, private modalController: ModalController) {}

  add(){

  }

  logout(){
    this.navController.navigateForward('/login');
  }

  incidents(){
    this.navController.navigateForward('/home');
  }

  task(){
    this.navController.navigateForward('/tasks');
  }

  async detail(){
    const modal = await this.modalController.create({
      component: MotoDetailPage, // Componente que deseas mostrar como modal
      cssClass: 'modal-class', // Clase CSS opcional para personalizar el estilo del modal
      componentProps: {
        isNew: false,
        detailMode: true
        // Puedes pasar propiedades adicionales al componente del modal si es necesario
      }
    });
  
    return await modal.present();
  }

  async newIncident(){
    const modal = await this.modalController.create({
      component: MotoDetailPage, // Componente que deseas mostrar como modal
      cssClass: 'modal-class', // Clase CSS opcional para personalizar el estilo del modal
      componentProps: {
        isNew: true,
        detailMode: false
        // Puedes pasar propiedades adicionales al componente del modal si es necesario
      }
    });
  
    return await modal.present();
  }
  
}
