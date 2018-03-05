import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { User } from '../.././modals/user';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';
import 'rxjs/add/operator/catch';
import { environment } from '../../../environments/environment';
import { AuthenticationService } from '../authentication/authentication.service';

/**
 * This class provides the User service with methods to manage.
 */


@Injectable()
export class UserService {
  httpOptions: any;
  roles: any[];
  /**
   * Creates a new KnowledgeBaseContentervice with the injected HttpClient.
   * @param {HttpClient} http - The injected HttpClient.
   * @constructor
   */
  private apiUrl = environment.API_ENDPOINT + 'users';  // URL to web api environment.API_ENDPOINT

  constructor(private http: HttpClient, private auth: AuthenticationService) {
      const token = auth.getAccessToken();
      this.httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': token })
    };
  }

  /**
   * Returns an Observable for the HTTP GET request for the JSON resource.
   * @return {User[]} The Observable for the HTTP request.
   */

   listUser (): Observable<any> {
    return this.http.get(this.apiUrl)
    .catch(this.handleErrorObservable);
  }

  activateDeactivateUsers (status, userId) : Observable<any> {
    return this.http.put(this.apiUrl+"/state/"+userId+"/"+status, {})
    .catch(this.handleErrorObservable);
  }


  /**
   * Returns an Observable for the HTTP GET request for the JSON resource.
   * @return {any[]} The Observable for the HTTP request.
   */

  listRoles (): Observable<any> {
    return this.http.get(this.apiUrl + '/roles')
    .catch(this.handleErrorObservable);
  }

  setRoles(data) {
    this.roles = data;
  }

  getRoles() {
    return this.roles;
  }

 /**
   * Returns an Observable for the HTTP GET request for the JSON resource.
   * @return {any[]} The Observable for the HTTP request.
   */

  listApprovers (): Observable<any> {
    // let url = this.apiUrl + '/approvers';
    const url  = 'api/approvers';
    return this.http.get(url)
    .catch(this.handleErrorObservable);
  }


  /**
   * Returns an Observable for the HTTP POST request for the JSON resource.
   * @return {User} The Observable for the HTTP request.
   */

  createUser (userInfo: User): Observable<any> {
        return this.http.post(this.apiUrl, {'user': userInfo}, this.httpOptions)
                   .catch(this.handleErrorObservable);
  }

  /**
   * Returns an Observable for the HTTP POST request for the JSON resource.
   * @return {KnowledgeBaseArticle} The Observable for the HTTP request.
   */

  updateUser (id: String, userInfo: User): Observable<any> {
    return this.http.put(this.apiUrl + '/' + id, {'user': userInfo}, this.httpOptions)
               .catch(this.handleErrorObservable);
  }

  /**
   * Returns an Observable for the HTTP POST request for the JSON resource.
   * @return {UserService} The Observable for the HTTP request.
   */

  reteriveUserById(userId: String): Observable<any> {
    return this.http.get(this.apiUrl + '/' + userId, this.httpOptions).catch(this.handleErrorObservable);
}

/**
   * Returns an Observable for the HTTP DELETE request for the JSON resource.
   * @return {UserService} The Observable for the HTTP request.
   */

  deleteUser (id: String): Observable<any> {
    return this.http.delete(this.apiUrl + '/' + id,  this.httpOptions)
               .catch(this.handleErrorObservable);
  }


/**
    * Handle HTTP error
    */
  private handleErrorObservable (error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.message || error);
  }
}

