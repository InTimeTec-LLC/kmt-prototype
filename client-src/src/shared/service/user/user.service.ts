import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { User } from '../.././modals/user';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';
import 'rxjs/add/operator/catch';
import { environment } from '../../../environments/environment';


/**
 * This class provides the User service with methods to manage.
 */

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class UserService {

  /**
   * Creates a new KnowledgeBaseContentervice with the injected HttpClient.
   * @param {HttpClient} http - The injected HttpClient.
   * @constructor
   */
  private apiUrl = 'api/users';  // URL to web api environment.API_ENDPOINT

  constructor(private http: HttpClient) { }

  /**
   * Returns an Observable for the HTTP GET request for the JSON resource.
   * @return {User[]} The Observable for the HTTP request.
   */

   listUser (): Observable<User[]> {
    return this.http.get(this.apiUrl)
    .catch(this.handleErrorObservable);

  }

  /**
   * Returns an Observable for the HTTP POST request for the JSON resource.
   * @return {User} The Observable for the HTTP request.
   */

  createUser (userInfo: UserService): Observable<User> {
        return this.http.post(this.apiUrl, userInfo, httpOptions)
                   .catch(this.handleErrorObservable);
}

  /**
   * Returns an Observable for the HTTP POST request for the JSON resource.
   * @return {KnowledgeBaseArticle} The Observable for the HTTP request.
   */

  updateUser (id: Number, userInfo: User): Observable<User> {
    return this.http.put(this.apiUrl + '/' + id, userInfo, httpOptions)
               .catch(this.handleErrorObservable);
  }

  /**
   * Returns an Observable for the HTTP POST request for the JSON resource.
   * @return {UserService} The Observable for the HTTP request.
   */

  reteriveUserById(userId: Number): Observable<User> {
    return this.http.get(this.apiUrl + '/' + userId).catch(this.handleErrorObservable);
}



/**
    * Handle HTTP error
    */
  private handleErrorObservable (error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.message || error);
  }
}

