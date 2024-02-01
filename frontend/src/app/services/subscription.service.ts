import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private endpoint = 'http://localhost:8080/subscription';
  token = localStorage.getItem( 'token' );

  constructor(
    private http: HttpClient) { }


  get() {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + `${this.token}`
    });

    try {
      return this.http.get(this.endpoint, { headers: headers });
    } catch (error: any) {
      debugger
      if(error.toString().includes("401")) {
        localStorage.removeItem('token');
      }
    }
    return of([]);
  }

  payment(subscriptionId: number) {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + `${this.token}`
    });

    try {
      const url = this.endpoint+"/payment/"+ subscriptionId;
      return this.http.get(url, { headers: headers });
    } catch (error: any) {
      debugger
      if(error.toString().includes("401")) {
        localStorage.removeItem('token');
      }
    }
    return of([]);
  }
}
