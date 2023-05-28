import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule, ModalController, NavParams } from '@ionic/angular';

@Component({
  selector: 'app-moto-detail',
  templateUrl: './moto-detail.page.html',
  styleUrls: ['./moto-detail.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule]
})
export class MotoDetailPage implements OnInit {
  @Input() detailMode?: boolean;
  @Input() isNew?: boolean;

  constructor(private modalController: ModalController) {
  }

  ngOnInit() {
    //recoger todo pa indicar si es detalle o anadir
    console.log(this.isNew)

  }

  editOn() {
    if (this.detailMode){
      this.detailMode = false;
      return;
    }
    this.detailMode = true;
  }

  closeModal(data?: any) {
    console.log(this.isNew)
    this.modalController.dismiss(data)
  }
}
