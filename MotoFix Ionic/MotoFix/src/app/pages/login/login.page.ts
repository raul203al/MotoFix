import { Component, ElementRef, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { IonicModule, NavController } from '@ionic/angular';
import { PermissionsService } from 'src/services/permissions.service';
import { SqliteService } from 'src/app/services/sqlite.service';
import { ViewChild } from '@angular/core';
import { UtilsService } from 'src/app/services/utils.service';
import Swal from 'sweetalert2'



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
    private formBuilder: FormBuilder,
    private permission: PermissionsService,
    private db: SqliteService,
    private utils: UtilsService,
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
    this.permission.checkPermissions();
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
      Swal.fire({
        title: 'Error',
        text: 'El nombre de usuario o contraseña son incorrectos',
        icon: 'error',
        confirmButtonText: 'Aceptar'
      })
      console.log('No existe')
      return;
    }

    this.utils.navigateReplacingUrl('/home');
    console.log(user)

  }

  async createAccount() {
    if (this.loginForm.get('userRegister')?.value === undefined || this.loginForm.get('passwordRegister')?.value === undefined) {
      return;
    }

    let user = await this.db.getUser(this.loginForm.get('userRegister')?.value, this.loginForm.get('passwordRegister')?.value);
    if (user.length > 0) {
      Swal.fire({
        title: 'Error',
        text: 'El usuario ya existe',
        icon: 'error',
        confirmButtonText: 'Aceptar'
      })
      console.log('Ya existe');
      return;
    }

    await this.db.addUser(this.loginForm.get('userRegister')?.value, this.loginForm.get('passwordRegister')?.value).then(() => {
      console.log('Cuenta creada')
      this.chkCheckbox!.nativeElement.checked = false; // Establece el valor del checkbox en false
      this.loginForm.reset(); // Vaciar los campos del formulario
      Swal.fire({
        text: 'El usuario ha sido creado correctamente',
        icon: 'success',
        confirmButtonText: 'Aceptar'
      })

      //Mostrar un swal y cambiar
    }).catch((error) => {
      //Mostrar error
      Swal.fire({
        title: 'Error',
        text: 'Error al registar en la base de datos',
        icon: 'error',
        confirmButtonText: 'Aceptar'
      })
    })
  }

  public authStatus(): boolean {
    let disabled: boolean = false;
    if (this.loginForm.get('user')?.value.length == 0 || this.loginForm.get('password')?.value.length == 0) {
      disabled = true;
    }
    return disabled;
  }

  public registerStatus(): boolean {
    let disabled: boolean = false;
    if (this.loginForm.get('userRegister')?.value.length == 0 || this.loginForm.get('passwordRegister')?.value.length == 0) {
      disabled = true;
    }
    return disabled;
  }
}
