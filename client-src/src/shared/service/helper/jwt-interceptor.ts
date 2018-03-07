import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';


@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(
        private spinnerService: Ng4LoadingSpinnerService
    ) {}
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        if (request instanceof HttpRequest) {
            this.spinnerService.show();
        }
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.success && currentUser.success.accessToken) {
            request = request.clone({
                setHeaders: {
                    Authorization: currentUser.success.accessToken
                }
            });
        }

        return next.handle(request).do((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
               this.spinnerService.hide();
            }
          }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
               this.spinnerService.hide();
            }
        });
    }
}
