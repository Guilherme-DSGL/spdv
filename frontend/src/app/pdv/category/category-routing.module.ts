import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriesFormComponent } from './categories-form/categories-form.component';
import { CategoriesTableComponent } from './categories-table/categories-table.component';
import { PdvComponent } from '../pdv.component';
import { AuthGuard } from 'src/app/guard/auth.guard';

const routes: Routes = [
  {path: 'pdv/categories', canActivate:[AuthGuard], component: PdvComponent, children:[
    {path: 'form', component:CategoriesFormComponent},
    {path: 'form/:id', component:CategoriesFormComponent},
    {path: 'table', component:CategoriesTableComponent}
  ]}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoryRoutingModule { }
