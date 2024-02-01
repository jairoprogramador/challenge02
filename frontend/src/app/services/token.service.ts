import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private tokenEndpoint = 'http://localhost:8080/oauth2/token';
  private clientId = 'company';
  private clientSecret = 'secret';

  constructor(
    private http: HttpClient) { }

  async getToken(code?: string): Promise<string> {
    if(code){
      const $token = this.obtenerToken(code);
      const response: any = await lastValueFrom($token);
      const token = response?.access_token || "";
      if(token?.length > 0){
        localStorage.setItem('token', token);
      }
    }
    let token = localStorage.getItem( 'token' );
    return token || "";
  }

  obtenerToken(code: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Access-Control-Allow-Origin': '*',
      'Authorization': 'Basic ' + btoa(`${this.clientId}:${this.clientSecret}`)
    });

    const body = `grant_type=authorization_code&redirect_uri=http://localhost:4200/&code=${code}`;

    return this.http.post(this.tokenEndpoint, body, { headers: headers });
  }

}
