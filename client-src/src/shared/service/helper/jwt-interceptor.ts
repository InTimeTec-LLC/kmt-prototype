import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Router } from '@angular/router';
import { MessageService } from '../message/message';
import { AuthenticationService } from '../authentication/authentication.service';



@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(
        private spinnerService: Ng4LoadingSpinnerService,
        private auth: AuthenticationService,
        private router: Router,
        private messageService: MessageService
    ) {}
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        if (request instanceof HttpRequest) {
            console.log('Start Request for Spinner');
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
               console.log('SUCCESS: Stop Request for Spinner');
               this.spinnerService.hide();
            }
          }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
               console.log('ERROR: Stop Request for Spinner');
               this.spinnerService.hide();
               if (err.error.success.message === 'Unauthorized access') {
                    this.messageService.sendMessage('closeMatDrawer');
                    this.auth.logout();
                    this.router.navigate(['/login']);
               }
            }
        });
    }
}
