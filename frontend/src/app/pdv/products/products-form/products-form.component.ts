import { Component, ViewChild } from '@angular/core';
import {  productDTOFromGroupForm, productFromGroupForm, productToGroupForm} from '../product';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponseMessagesService, statusNumber } from 'src/app/app-services/http-response-messages.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ProductService } from 'src/app/app-services/product.service';
import { ProductValidatorMessages } from '../productValidatorMessage';
import { CategoryService } from 'src/app/app-services/category.service';
import { Category, compareCategory } from '../../category/category';

@Component({
  selector: 'app-products-form',
  templateUrl: './products-form.component.html',
  styleUrls: ['./products-form.component.scss']
})
export class ProductsFormComponent {
  category:  Category = new Category();
  categories: Category[] = [];
  productValidatorMessages: ProductValidatorMessages;
  compareCategory = compareCategory;

  
  @ViewChild('f') myNgForm: any;
  
  formProduct = this.fob.group(
    {
      id: [undefined],
      name: ['', [Validators.required,Validators.minLength(3),]],
      brand: ['', [Validators.required, Validators.minLength(8),]],
      price: [1, [Validators.required]],
      purchasePrice: [1, [Validators.required]],
      stock: [1, [Validators.required]],
      category: [null, [Validators.required]]
    }
  );
  
  constructor(
    private fob: FormBuilder, 
    private service: ProductService,  
    private categoryService: CategoryService,
    private activatedRoute: ActivatedRoute,
    private httpResponseMessages: HttpResponseMessagesService,
    ){
    this.productValidatorMessages = new ProductValidatorMessages();
  }

 
  ngOnInit(): void {
    this.formProduct.controls['id'].disable();
    let params: any = this.activatedRoute.params
    this.getById(params.value.id);
    this.getCategories();
    
  }

  getCategories(){
    this.categoryService.getAll().subscribe(
      {
        next:  categories => this.categories = categories,
        error: (error: HttpErrorResponse) =>  this.httpResponseMessages.getErrorResponse(error),
      }
  )}

  getFieldIsValid(field: FormControl): boolean{
    return field.invalid && (field.touched || field.dirty);
  }

  resetForm(): void{
    this.myNgForm.resetForm();
  }
  
  submit(){
    let hasId = this.formProduct.controls['id'].value;
    (!hasId) ? this.create() : this.update();
  }

  create(){
    let productDTO = productDTOFromGroupForm(this.formProduct);
    console.log(productDTO);
    this.service.create(productDTO).subscribe({
      next: response => {
        this.httpResponseMessages.getSucessResponse(statusNumber.CREATED)
        this.formProduct = productToGroupForm(response, this.formProduct);
        console.log(this.formProduct);
      },
      error: (error: HttpErrorResponse) =>  this.httpResponseMessages.getErrorResponse(error),
    });
    this.resetForm();
  }
  update(){
    let product = productFromGroupForm(this.formProduct);
    this.service.update(product).subscribe({
     next:  response => {
      this.httpResponseMessages.getSucessResponse(statusNumber.UPDATED)
     },
     error:  (error: HttpErrorResponse) =>  this.httpResponseMessages.getErrorResponse(error),
    }
    );
  
  }
  getById(id: number){
    if(id){ 
    this.service.getById(id).subscribe(
      {
        next: (response) => {
          this.formProduct = productToGroupForm(response, this.formProduct)
        },
        error: (error: HttpErrorResponse) =>  this.httpResponseMessages.getErrorResponse(error),
      }
    ) 
    }
  }
}
