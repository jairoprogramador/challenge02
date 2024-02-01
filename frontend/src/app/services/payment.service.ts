import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private endpoint = 'http://localhost:8080/payment';

  constructor(
    private http: HttpClient) { }


  get(idUser: number) {
    let token = localStorage.getItem( 'token' );
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + `${token}`
    });
    try {
      let url = this.endpoint+"/user/"+idUser;
      return this.http.get(url, { headers: headers });
    } catch (error: any) {
      if(error.toString().includes("401")) {
        localStorage.removeItem('token');
      }
    }
    return of([]);
  }

  create(payment: any) {
    let token = localStorage.getItem( 'token' );
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + `${token}`
    });

    try {
      return this.http.post(this.endpoint, payment, { headers: headers });
    } catch (error: any) {
      if(error.toString().includes("401")) {
        localStorage.removeItem('token');
      }
    }
    return of([]);
  }
}
