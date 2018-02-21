import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Content } from '../.././modals/knowledge-base-content';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

// import 'rxjs/add/operator/do';  // for debugging

/**
 * This class provides the Knowledgebase content service with methods to manage.
 */
@Injectable()
export class KnowledgeBaseContentService {

  /**
   * Creates a new KnowledgeBaseContentervice with the injected HttpClient.
   * @param {HttpClient} http - The injected HttpClient.
   * @constructor
   */
  constructor(private http: HttpClient) { }

  /**
   * Returns an Observable for the HTTP GET request for the JSON resource.
   * @return {string[]} The Observable for the HTTP request.
   */

   listKnowledgeBaseContent (): Observable<Content[]> {
      return this.http.get<Content[]>('assets/data.json');
      /*.pipe(
        tap(contents => this.log(`fetched contents`)),
        catchError(this.handleError('listKnowledgeBaseContent', []))
      );*/
  }

  /**
    * Handle HTTP error
    */
  private handleError(error: any) {
    const errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg); // log to console instead
    return Observable.throw(errMsg);
  }
}

