import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from "./app.component";

const routes: Routes = [
    { path: '', component: AppComponent,
      children: [
        { path: '', loadChildren: () => import('./login/login-routing.module').then(m => m.LoginRoutingModule) },
        { path: 'login', loadChildren: () => import('./login/login-routing.module').then(m => m.LoginRoutingModule) },
        { path: 'dashboard', loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule) },
      ]  
    },
    { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
