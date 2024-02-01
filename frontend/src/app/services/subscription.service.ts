import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private endpoint = 'http://localhost:8080/subscription';

  constructor(
    private http: HttpClient) { }


  get() {
    let token = localStorage.getItem( 'token' );
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + `${token}`
    });

    try {
      return this.http.get(this.endpoint, { headers: headers });
    } catch (error: any) {
      if(error.toString().includes("401")) {
        localStorage.removeItem('token');
      }
    }
    return of([]);
  }
}
