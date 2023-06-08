import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { IonicModule, ModalController, NavParams } from '@ionic/angular';
import { PhotoService } from 'src/app/services/photo.service';
import { SqliteService } from 'src/app/services/sqlite.service';
import { Incident, Problem, UserPhoto } from 'src/app/clases/common-entities';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-moto-detail',
  templateUrl: './moto-detail.page.html',
  styleUrls: ['./moto-detail.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, ReactiveFormsModule]
})
export class MotoDetailPage implements OnInit {
  incidentForm: FormGroup;

  @Input() detailMode?: boolean;
  @Input() isNew?: boolean;
  @Input() incident?: Incident;

  dateEntry: any;
  dateExit: any;
  photo?: UserPhoto;

  constructor(
    private formBuilder: FormBuilder, private modalController: ModalController, public photoService: PhotoService, private db: SqliteService) {
    this.incidentForm = this.formBuilder.group({
      registration: ['', [Validators.required]],
      model: ['', [Validators.required]],
      problem: ['', [Validators.required]],
      description: ['', [Validators.required]]
    });
  }

  ngOnInit() {
    //recoger todo pa indicar si es detalle o anadir
    let dateEt = new Date(this.incident ? this.incident?.dateEntry : this.dateEntry)
    let dateEx = new Date(this.incident ? this.incident?.dateExit : this.dateExit)

    console.log(this.incident)
    this.incidentForm.get('registration')?.setValue(this.incident?.registration)
    this.incidentForm.get('model')?.setValue(this.incident?.model)
    this.incidentForm.get('problem')?.setValue(this.incident?.problem)
    this.incidentForm.get('description')?.setValue(this.incident?.description)
    this.dateEntry = dateEt
    this.dateExit = dateEx
  }

  editOn() {
    if (this.detailMode) {
      this.detailMode = false;
      return;
    }
    this.detailMode = true;
  }

  async addIncident() {
    if (this.photo && this.photo.webviewPath) {
      let incident: Problem = {
        id: -1,
        registration: this.incidentForm.get('registration')?.value,
        model: this.incidentForm.get('model')?.value,
        problem: this.incidentForm.get('problem')?.value,
        description: this.incidentForm.get('description')?.value,
        dateEntry: await this.formatDate(this.dateEntry),
        dateExit: await this.formatDate(this.dateExit),
        priority: '1',
        image: await this.photoService.convertImageToUint8Array(await this.photo.webviewPath)
      }
      console.log(incident)
      this.db.newIncident(incident.registration, incident.model, incident.problem, incident.description, incident.dateEntry, incident.dateExit, incident.priority, incident.image).then(() => {
        Swal.fire({
          text: 'Incidencia creada correctamente',
          icon: 'success',
          confirmButtonText: 'Aceptar'
        })
        this.closeModal();
      });
    }
  }

  async updateIncident() {
    if (this.photo && this.photo.webviewPath) {
      let incident: Problem = {
        id: -1,
        registration: this.incidentForm.get('registration')?.value,
        model: this.incidentForm.get('model')?.value,
        problem: this.incidentForm.get('problem')?.value,
        description: this.incidentForm.get('description')?.value,
        dateEntry: await this.formatDate(this.dateEntry),
        dateExit: await this.formatDate(this.dateExit),
        priority: '1',
        image: await this.photoService.convertImageToUint8Array(await this.photo.webviewPath)
      }
      console.log(incident)
      try {
        this.db.updateIncident(incident.registration, incident.model, incident.problem, incident.description, this.incident!.dateEntry, this.incident!.dateExit, incident.priority, incident.image, this.incident!.id);

        Swal.fire({
          text: 'Incidencia actualizada correctamente',
          icon: 'success',
          confirmButtonText: 'Aceptar'
        })
        this.closeModal();
      } catch (error) {
        Swal.fire({
          text: 'No se pudo actualizar',
          icon: 'error',
          confirmButtonText: 'Aceptar'
        })
      }

    }
  }

  deleteIncident() {
    try {
      this.db.deleteIncident(this.incident!.id)
      Swal.fire({
        text: 'Incidencia eliminada correctamente',
        icon: 'success',
        confirmButtonText: 'Aceptar'
      })
      this.closeModal();
    } catch (error) {
      Swal.fire({
        text: 'No se pudo actualizar',
        icon: 'error',
        confirmButtonText: 'Aceptar'
      })
    }
  }


  checkDate() {
    if (new Date(this.dateEntry) > new Date(this.dateExit)) {
      Swal.fire({
        title: 'Error',
        text: 'La fecha de inicio no puede se mayor a la de fin',
        icon: 'error',
        confirmButtonText: 'Aceptar'
      });
      this.dateEntry = ''
      this.dateExit = ''
      return; // Abort further execution
    }
  }

  async takePhoto() {
    if (!this.detailMode) {
      this.photo = await this.photoService.addNewToGallery();
      return;
    }
  }

  async formatDate(date: string): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (!date) {
        reject('Fecha inválida');
        return;
      }

      const dateObj = new Date(date);
      if (isNaN(dateObj.getTime())) {
        reject('Fecha inválida');
        return;
      }

      const dateOptions: Intl.DateTimeFormatOptions = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
      };
      const fDate = new Intl.DateTimeFormat('es-ES', dateOptions).format(dateObj);
      resolve(fDate);
    });
  }



  closeModal(data?: any) {
    console.log(this.isNew)
    this.modalController.dismiss(data)
  }
}
