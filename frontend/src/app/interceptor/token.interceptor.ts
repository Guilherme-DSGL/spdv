import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

export const AUTHORIZATION = 'Authorization';
export const BEARER = 'Bearer ';
export const keyAcessToken = 'acess-token';
@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  
  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let jsonToken = JSON.parse(localStorage.getItem(keyAcessToken)!);
    console.log(jsonToken);
    if(jsonToken){
     let token = jsonToken.token;
     if(request.url.search('auth') == -1){
      console.log('fazendo fetch fora do auth')
     request = request.clone(
      {
        setHeaders: {
          AUTHORIZATION : BEARER + token,
        }
      }
     );
    }
  }
    return next.handle(request);
  }
}
