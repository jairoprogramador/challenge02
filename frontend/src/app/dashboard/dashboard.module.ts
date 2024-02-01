import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { SubscriptionComponent } from './subscription/subscription.component';


@NgModule({
  declarations: [
    DashboardComponent,
    SubscriptionComponent
  ],
  imports: [
    DashboardRoutingModule,
    CommonModule
  ],
  exports: [SubscriptionComponent]
})
export class DashboardModule { }
