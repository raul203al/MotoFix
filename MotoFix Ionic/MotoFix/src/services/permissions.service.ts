import { Injectable } from '@angular/core';
import { AndroidPermissions } from '@ionic-native/android-permissions/ngx';
import { Diagnostic } from '@ionic-native/diagnostic/ngx';

@Injectable({
  providedIn: 'root',
})
export class PermissionsService {
  constructor(
    private permission: AndroidPermissions,
    private diagnostic: Diagnostic
  ) {}

  async checkStorage(){
    const isGranted = await this.diagnostic.getExternalStorageAuthorizationStatus();
    
    if (!(isGranted === this.diagnostic.permissionStatus.GRANTED)){
      await this.requestStorage();
      return;
    }

    console.log('Permiso Aceptado')
  }

  async requestStorage(){
    this.permission.requestPermission('android.permission.WRITE_EXTERNAL_STORAGE').then(result => {
      if (result.hasPermission){
        console.log('Permitido')
      }
    })
  }

}
