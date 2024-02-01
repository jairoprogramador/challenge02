import { Component, OnInit } from '@angular/core';
import { SubscriptionService } from '../../services/subscription.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

interface SubscriptionOneseven {
  id: number;
  type: string;
  price: number;
}

@Component({
  selector: 'app-subscription',
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.scss']
})
export class SubscriptionComponent implements OnInit {

  mensaje403: string = "";

  mensaje: string = "";
  public subscriptions: SubscriptionOneseven[] = [];

  constructor(
    private subscriptionService: SubscriptionService,
    private router: Router) { }

  ngOnInit() {
    this.getSubscriptions();
    setInterval(() => {
      this.resetSms();
    }, 5000);
  }

  async getSubscriptions(): Promise<void> {
    let response = this.subscriptionService.get();
    response.subscribe(
      result => {
        this.subscriptions = result as SubscriptionOneseven[];
      },
      error => this.handlerError(error)
    );
  }

  async createPayment(sub: any): Promise<void> {
    let response = this.subscriptionService.payment(sub.id);
    response.subscribe(
      (result: any) => {
        console.log(result);
        window.open(result.url, "_self");
      },
      error => this.handlerError(error)
    );
  }

  handlerError(error: any) {
    let response = error as HttpErrorResponse;
    if (response.status == 403) {
      this.mensaje403 = "No tienes permiso al recurso";
    }
    if (response.status == 401 || response.status == 0) {
      localStorage.removeItem('token');
      this.router.navigate(['./']);
    }else{
      this.mensaje = "Error al realizar la operación";
    }
  }

  resetSms(): void {
    this.mensaje = "";
  }

}
