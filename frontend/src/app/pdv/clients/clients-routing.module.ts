import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientFormComponent } from './client-form/client-form.component';
import { ClientsTableComponent } from './clients-table/clients-table.component';
import { PdvComponent } from '../pdv.component';
import { AuthGuard } from 'src/app/guard/auth.guard';

export const routes: Routes = [
  {path: 'pdv/client', canActivate:[AuthGuard], component: PdvComponent, children:[
  {path: 'form', component:ClientFormComponent},
  {path: 'form/:id', component:ClientFormComponent},
  {path: 'table', component:ClientsTableComponent},
  {path: '', redirectTo: 'table', pathMatch:'full'}
  ]}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientsRoutingModule { }
