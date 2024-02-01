import { Component, OnInit } from '@angular/core';
import { PaymentService } from '../services/payment.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { jwtDecode } from "jwt-decode";

interface SubscriptionOneseven {
  id: number;
  type: string;
  price: number;
}

interface User {
  id: number;
  email: string;
  name: string;
  lastName: string;
}

interface Payment {
  user: User;
  subscription: SubscriptionOneseven;
  expirationDate: string;
}

interface PaymentView {
  name: string;
  email: string;
  type: string;
  expiration: string
}

const decoded = jwtDecode(localStorage.getItem('token') || "");

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  mensaje403: string = "";
  mensajeError: string = "";

  payment = {} as Payment;
  paymentView = {} as PaymentView;
  expirationDate = {} as Date;

  constructor(
    private paymentService: PaymentService,
    private router: Router) { }

  ngOnInit() {
    this.checkParam();
    setInterval(() => {
      this.resetSms();
    }, 5000);
  }

  private checkParam(){
    const url = new URL(window.location.href);
    const params = new URLSearchParams(url.search);
    if(params.has("uId") && params.has("sId")){
      const uId: string = params.get('uId') || "";
      const sId: string = params.get('sId') || "";
      
      if(uId != "-1" && sId != "-1"){
        this.pagar(parseInt(uId), parseInt(sId));
      }else{
        this.mensajeError = "Error en el pago";
        this.paymentView = {} as PaymentView;
      }
    }
    else{
      this.getPayments();
    }
  }

  async getPayments(): Promise<void> {
    let user: any = decoded;
    let response = this.paymentService.get(user.userId);
    response.subscribe(
      result => {
        const payments = result as Payment[];
        if(payments.length != 0){
          this.payment = payments[0];
          this.paymentView = {
            name: this.payment.user.name +" "+ this.payment.user.lastName,
            email: this.payment.user.email,
            type: this.payment.subscription.type,
            expiration: this.payment.expirationDate
          }
          this.expirationDate = new Date(this.payment.expirationDate);
        }else{
          this.paymentView = {} as PaymentView;
        }
      },
      error => console.log(error)
    );
  }

  async pagar(userId: number, subsId: number): Promise<void> {
    let response = this.paymentService.create({userId: userId,subscriptionId: subsId});
    response.subscribe(
      result => {
        window.open("http://localhost:4200/dashboard", "_self");
      },
      error => console.log(error)
    );
  }

  handlerError(error: any) {
    this.paymentView = {} as PaymentView;
    let response = error as HttpErrorResponse;
    if(response.status == 403) {
      this.mensaje403 = "No tienes permiso al recurso";
    }
    if(response.status == 401 || response.status == 0) {
          localStorage.removeItem('token');
          this.router.navigate(['./']);
    }
  }

  resetSms(): void {
    this.mensajeError = "";
    this.mensaje403 = "";
  }
}
