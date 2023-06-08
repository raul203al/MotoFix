import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(private router: Router) { }

  public navigateReplacingUrl(route: string, payload?: any) {

    if (payload) this.router.navigate([route, payload], { replaceUrl: true }); // Para que se envíe con exito debes mandar el payload como objeto (ej: un json) y no como un primitivo (ej: number, string). Obtener datos (route: ActivatedRoute): this.route.params.pipe(take(1)).subscribe((params: Params) => params;

    else this.router.navigate([route], { replaceUrl: true });

  }
}
