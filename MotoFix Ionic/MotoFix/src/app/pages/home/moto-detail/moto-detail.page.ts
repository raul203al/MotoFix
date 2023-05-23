import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-moto-detail',
  templateUrl: './moto-detail.page.html',
  styleUrls: ['./moto-detail.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule]
})
export class MotoDetailPage implements OnInit {
  detailMode: boolean = true;

  constructor() { }

  ngOnInit() {
  }


  editOn() {
    if (!this.detailMode) {
      this.detailMode = true;
      return;
    }
    this.detailMode = false;
  }
}
