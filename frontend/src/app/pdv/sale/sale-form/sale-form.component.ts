import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponseMessagesService, statusNumber } from 'src/app/app-services/http-response-messages.service';
import {  saleDTOFromGroupForm, saleFromGroupForm, saleToGroupForm } from '../sale';
import { HttpErrorResponse } from '@angular/common/http';
import { SaleService } from 'src/app/app-services/sale.service';
import { SaleValidatorMessages } from '../saleValidatorsMessages';

import { ClientService } from 'src/app/app-services/client.service';
import { ProductService } from 'src/app/app-services/product.service';
import { Client, compareClient } from '../../clients/client';
import { Product, compareProduct } from '../../products/product';

@Component({
  selector: 'app-sale-form',
  templateUrl: './sale-form.component.html',
  styleUrls: ['./sale-form.component.scss']
})
export class SaleFormComponent  {
  clients: Client[] = []; 
  products: Product[] = [];
  saleValidatorMessages: SaleValidatorMessages;
  saleType = ['12x','11x', '10x', '9x', '8x', '7x', '6x', '5x', '4x', '3x', '2x', '1x',];
  compareClient = compareClient;
  compareProduct = compareProduct;
  
  @ViewChild('f') myNgForm: any;
  
  formSale = this.fob.group(
    {
      id: [undefined],
      client: [null, [Validators.required,Validators.minLength(3),]],
      product: [new Product(), [Validators.required, Validators.minLength(8),]],
      salePrice: [0, [Validators.required]],
      saleType: ['', [Validators.required]],
      saleDate: [new Date(), [Validators.required]],
    }
  );
 
  constructor(
    private fob: FormBuilder, 
    private service: SaleService,  
    private serviceClient: ClientService,
    private serviceProduct: ProductService,
    private activatedRoute: ActivatedRoute,
    private httpResponseMessages: HttpResponseMessagesService,
    ){
    this.saleValidatorMessages = new SaleValidatorMessages();
  }

  
  
  ngOnInit(): void {
    this.formSale.controls['id'].disable();
    let params: any = this.activatedRoute.params
    this.getById(params.value.id);
    this.initializeFields();
    
  }
  initializeFields(){
    this.serviceClient.getAll().subscribe(clients => this.clients = clients);
    this.serviceProduct.getAll().subscribe(products => this.products = products);
    this.formSale.get("product")!.valueChanges.subscribe( product => {
        this.formSale.controls['salePrice'].setValue(product?.price ?? 0);
    });
  }

  getFieldIsValid(field: FormControl): boolean{
    return field.invalid && (field.touched || field.dirty);
  }

  resetForm(): void{
    this.myNgForm.resetForm();
  }
  
  submit(){
    let hasId = this.formSale.controls['id'].value;
    (!hasId) ? this.create() : this.update();
  }

  create(){
    let sale = saleDTOFromGroupForm(this.formSale);
    this.service.create(sale).subscribe({
      next: response => {
        this.httpResponseMessages.getSucessResponse(statusNumber.CREATED)
        console.log(response)
        this.formSale = saleToGroupForm(response, this.formSale);
        console.log(this.formSale.value)
      },
      error:(error: HttpErrorResponse) =>  this.httpResponseMessages.getErrorResponse(error),
    });
    this.resetForm();
  }
  update(){
    let sale = saleFromGroupForm(this.formSale);
    this.service.update(sale).subscribe({
     next:  response => {
      this.httpResponseMessages.getSucessResponse(statusNumber.UPDATED)
     },
     error: (error: HttpErrorResponse) =>  this.httpResponseMessages.getErrorResponse(error),
    }
    );
  
  }
  getById(id: number){
    if(id){ 
    this.service.getById(id).subscribe(
      {
        next:(response) => {
          this.formSale = saleToGroupForm(response, this.formSale)
        },
        error: (error: HttpErrorResponse) =>  this.httpResponseMessages.getErrorResponse(error),
      }
    ) 
    }
  }
}
