import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import { environment } from '../../../environments/environment';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()
export class AuthenticationService {
    public token: string;
    private loggedIn = new BehaviorSubject<boolean>(false); // {1}
    httpOptions: any;

    constructor(private http: HttpClient) {
        this.httpOptions = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json'})
          };

    }

    login(username: string, password: string) {
        return this.http.post<any>(environment.API_ENDPOINT + 'login', { email: username, password: password })
            .map(data => {
                // login successful if there's a jwt token in the response
                if (data && data.success && data.success.status) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    this.loggedIn.next(true);
                    localStorage.setItem('currentUser', JSON.stringify(data));
                }
                return data;
            });
    }

    forgotPassword(email: string): Observable<any> {
        return this.http.get(environment.API_ENDPOINT + 'forgotpassword?emailid=' + email, this.httpOptions).
        catch(this.handleErrorObservable);
    }

    logout(): void {
        // clear token remove user from local storage to log user out
        this.token = null;
        localStorage.removeItem('currentUser');
        this.loggedIn.next(false);
    }

    isAuthenticated(): any {
        if (this.getAccessToken() != null) {
            this.loggedIn.next(true);
        } else {
            this.loggedIn.next(false);
        }
        return this.loggedIn.asObservable(); // {2}
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
        if (currentUser && currentUser.success && currentUser.user) {
            return currentUser.user.id;
        }
        return null;
    }

    getUserRole(): string {
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.success && currentUser.user) {
            return currentUser.user.userRole;
        }
        return null;
    }

    /**
    * Handle HTTP error
    */
  private handleErrorObservable (error: Response | any) {
    console.error(error.success || error);
    return Observable.throw(error.success || error);
  }


}