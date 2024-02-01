import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private tokenEndpoint = 'http://localhost:8080/user';

  constructor(
    private http: HttpClient) { }


  save(user: any) {
    try {
      return this.http.post(this.tokenEndpoint, user);
    } catch (error: any) {
      if(error.toString().includes("401")) {
        localStorage.removeItem('token');
      }
    }
    return of([]);
  }
}
