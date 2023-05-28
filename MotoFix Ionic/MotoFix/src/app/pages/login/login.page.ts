import { Component, ElementRef, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { IonicModule, NavController } from '@ionic/angular';
import { VariablesService } from 'src/utils/variables.service';
import { PermissionsService } from 'src/services/permissions.service';
import { SqliteService } from 'src/app/services/sqlite.service';
import { ViewChild } from '@angular/core';


@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, IonicModule],
})
export class LoginPage implements OnInit {
  loginForm: FormGroup;

  @ViewChild('chk', { static: false }) chkCheckbox?: ElementRef<HTMLInputElement>;

  constructor(
    private variables: VariablesService,
    private formBuilder: FormBuilder,
    private permission: PermissionsService,
    private db: SqliteService,
    private navController: NavController
  ) {
    this.loginForm = this.formBuilder.group({
      user: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9_]+$/)]],
      password: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9_]+$/)]],
      userRegister: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9_]+$/)]],
      passwordRegister: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9_]+$/)]]
    });
  }

  ngOnInit() {
    this.permission.checkStorage()
  }

  async login() {
    if (this.loginForm.get('user')?.value === null || this.loginForm.get('password')?.value === null) {
      return;
    }
    let users = await this.db.getUsers();
    console.log(users)
    let user = await this.db.getUser(this.loginForm.get('user')?.value, this.loginForm.get('password')?.value);
    console.log(user)
    if (user[0] == undefined) {
      console.log('No existe')
      return;
    }

    this.navController.navigateForward('/home');
    console.log(user)

  }

  async createAccount() {
    if (this.loginForm.get('userRegister')?.value === undefined || this.loginForm.get('passwordRegister')?.value === null) {
      return;
    }

    let user = await this.db.getUser(this.loginForm.get('userRegister')?.value, this.loginForm.get('passwordRegister')?.value);
    if (user.length > 0) {
      console.log('Ya existe');
      return;
    }

    await this.db.addUser(this.loginForm.get('userRegister')?.value, this.loginForm.get('passwordRegister')?.value).then(() => {
      console.log('Cuenta creada')
      this.chkCheckbox!.nativeElement.checked = false; // Establece el valor del checkbox en false
      //Mostrar un swal y cambiar
    }).catch((error) => {
      //Mostrar error
    })
  }
}
