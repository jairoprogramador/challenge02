import { Component, OnInit } from '@angular/core';
import { TokenService } from '../services/token.service';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';

interface User {
  id: number;
  password: string;
  email: string;
  name: string;
  lastName: string;
  newsletters: boolean
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  mensaje: string = "";

  checkoutForm = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
    name: new FormControl(''),
    lastName: new FormControl(''),
    newsletters: new FormControl('')
  });

  constructor(
    private userService: UserService,
    private tokenService: TokenService,
    private router: Router) { }

  ngOnInit() {
    this.getToken();
    setInterval(() => {
      this.resetSms(); 
    }, 5000);
  }

  async getToken(): Promise<void> {
    const url = new URL(window.location.href);
    const params = new URLSearchParams(url.search);
    let token: string = "";
    if(params.has("code")){
      const code: string = params.get('code') || "";
      token = await this.tokenService.getToken(code);
    }else{
      token = await this.tokenService.getToken();
    }

    if(token?.length > 0){
      this.router.navigate(['./dashboard']);
    }
  }

  async create(): Promise<void> {
    let user = this.checkoutForm.value;
    let response = this.userService.save(user);
    response.subscribe(
      result => {
        const user = result as User;
        this.mensaje = "Usuario creado con exito";
        this.checkoutForm.reset();
      },
      error => console.log(error)
    );
  }

  resetSms(): void {
    this.mensaje = "";
  }

}