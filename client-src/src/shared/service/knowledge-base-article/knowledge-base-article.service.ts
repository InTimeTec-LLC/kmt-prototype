import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { KnowledgeBaseArticle } from '../.././modals/knowledge-base-article';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';
import 'rxjs/add/operator/catch';


/**
 * This class provides the Knowledgebase content service with methods to manage.
 */

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class KnowledgeBaseArticleService {

  /**
   * Creates a new KnowledgeBaseContentervice with the injected HttpClient.
   * @param {HttpClient} http - The injected HttpClient.
   * @constructor
   */
  private apiUrl = 'api/articles';  // URL to web api

  constructor(private http: HttpClient) { }

  /**
   * Returns an Observable for the HTTP GET request for the JSON resource.
   * @return {string[]} The Observable for the HTTP request.
   */

   listKnowledgeBaseArticle (): Observable<KnowledgeBaseArticle[]> {
    return this.http.get(this.apiUrl)
    .catch(this.handleErrorObservable);

  }

  /**
   * Returns an Observable for the HTTP POST request for the JSON resource.
   * @return {string[]} The Observable for the HTTP request.
   */

  createKnowledgeBaseArticle (articleInfo: KnowledgeBaseArticle): Observable<KnowledgeBaseArticle> {
        return this.http.post(this.apiUrl, articleInfo, httpOptions)
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

