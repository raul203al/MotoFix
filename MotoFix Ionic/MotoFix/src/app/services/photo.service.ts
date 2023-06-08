import { Injectable } from '@angular/core';
import { Camera, CameraResultType, CameraSource, Photo } from '@capacitor/camera';
import { UserPhoto } from '../clases/common-entities';

@Injectable({
  providedIn: 'root'
})
export class PhotoService {
  public photos: UserPhoto[] = [];

  constructor() { }

  public async addNewToGallery(): Promise<UserPhoto> {
    // Take a photo
    const capturedPhoto = await Camera.getPhoto({
      resultType: CameraResultType.Uri,
      source: CameraSource.Camera,
      quality: 100,

    });

    let ret: UserPhoto = {
      filepath: new Date().toString(),
      webviewPath: capturedPhoto.webPath
    }

    return ret;
  }

  async convertImageToUint8Array(imageUrl: string): Promise<Uint8Array> {
    const response = await fetch(imageUrl);
    const arrayBuffer = await response.arrayBuffer();
    const uint8Array = new Uint8Array(arrayBuffer);
    return uint8Array;
  }

  async convertBlobUrlToUint8Array(blobUrl: string): Promise<Uint8Array> {
    const response = await fetch(blobUrl);
    const blobData = await response.blob();
    return new Promise<Uint8Array>((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        if (reader.result instanceof ArrayBuffer) {
          const uint8Array = new Uint8Array(reader.result);
          resolve(uint8Array);
        } else {
          reject(new Error('Invalid blob data'));
        }
      };
      reader.onerror = reject;
      reader.readAsArrayBuffer(blobData);
    });
  }

  convertUint8ArrayToUserPhoto(data: Uint8Array): UserPhoto {
    const blob = new Blob([data], { type: 'image/jpeg' });
    const urlCreator = window.URL || window.webkitURL;
    const imageUrl = urlCreator.createObjectURL(blob);
  
    const userPhoto: UserPhoto = {
      filepath: '', // Asigna el valor deseado para el filepath
      webviewPath: imageUrl, // webviewPath es una URL válida para la imagen
    };
  
    return userPhoto;
  }
}
