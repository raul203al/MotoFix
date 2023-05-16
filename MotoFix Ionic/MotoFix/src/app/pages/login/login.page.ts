import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { IonicModule, NavController } from '@ionic/angular';
import { VariablesService } from 'src/utils/variables.service';
import { PermissionsService } from 'src/services/permissions.service';
import { DatabaseService } from 'src/services/database.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, IonicModule],
})
export class LoginPage implements OnInit {
  loginForm: FormGroup;

  constructor(
    private variables: VariablesService,
    private formBuilder: FormBuilder,
    private permission: PermissionsService,
    private db: DatabaseService
  ) {
    this.loginForm = this.formBuilder.group({
      user: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  ngOnInit() {
    this.permission.checkStorage()
  }

  login() {
    this.db.addUser(this.loginForm.get('user')?.value, this.loginForm.get('password')?.value)
  }

  createAccount() {
  }
}
