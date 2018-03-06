import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import { environment } from '../../../environments/environment';

@Injectable()
export class AuthenticationService {
    public token: string;

    constructor(private http: HttpClient) {

    }

    login(username: string, password: string) {
        return this.http.post<any>(environment.API_ENDPOINT + 'login', { email: username, password: password })
            .map(data => {
                // login successful if there's a jwt token in the response
                if (data && data.success && data.success.status) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(data));
                }

                return data;
            });
    }

    logout(): void {
        // clear token remove user from local storage to log user out
        this.token = null;
        localStorage.removeItem('currentUser');
    }

    isAuthenticated(): boolean {
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
         if (currentUser && currentUser.success && currentUser.success.accessToken) {
             return true;
         }
         return false;
    }

    getUserName(): string {
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
         if (currentUser && currentUser.success && currentUser.success.status) {
             return currentUser.user.firstName + ' ' + currentUser.user.lastName;
         }
         return null;
    }

    getUserType(): string {
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
         if (currentUser && currentUser.success && currentUser.success.status) {
             return currentUser.user.userRole;
         }
         return undefined;
    }

    getAccessToken(): string {
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
         if (currentUser && currentUser.success && currentUser.success.accessToken) {
             return currentUser.success.accessToken;
         }
         return null;
    }

    getUserId(): string {
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
         if (currentUser && currentUser.user && currentUser.accessToken) {
             return currentUser.user.id;
         }
         return null;
    }

}