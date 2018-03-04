import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { KnowledgeBaseArticle } from '../.././modals/knowledge-base-article';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';
import 'rxjs/add/operator/catch';
import { environment } from '../../../environments/environment';


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
  private apiUrl = 'api/articles';  // URL to web api environment.API_ENDPOINT

  constructor(private http: HttpClient) { }

  /**
   * Returns an Observable for the HTTP GET request for the JSON resource.
   * @return {KnowledgeBaseArticle[]} The Observable for the HTTP request.
   */

   listKnowledgeBaseArticle (): Observable<KnowledgeBaseArticle[]> {
    return this.http.get(this.apiUrl)
    .catch(this.handleErrorObservable);

  }

  /**
   * Returns an Observable for the HTTP GET request for the JSON resource.
   * @return {any[]} The Observable for the HTTP request.
   */

  listKnowledgeBaseArticleTypes (): Observable<any[]> {
    // let url = this.apiUrl + '/types';
    const url  = 'api/types';
    return this.http.get(url)
    .catch(this.handleErrorObservable);
  }

  /**
   * Returns an Observable for the HTTP POST request for the JSON resource.
   * @return {any} The Observable for the HTTP request.
   */

  createKnowledgeBaseArticle (articleInfo: KnowledgeBaseArticle): Observable<any> {
        return this.http.post(this.apiUrl, articleInfo, httpOptions)
                   .catch(this.handleErrorObservable);
}

  /**
   * Returns an Observable for the HTTP POST request for the JSON resource.
   * @return {any} The Observable for the HTTP request.
   */

  updateKnowledgeBaseArticle (id: Number, articleInfo: KnowledgeBaseArticle): Observable<any> {
    return this.http.put(this.apiUrl + '/' + id, articleInfo, httpOptions)
               .catch(this.handleErrorObservable);
  }

  /**
   * Returns an Observable for the HTTP POST request for the JSON resource.
   * @return {any} The Observable for the HTTP request.
   */

  reteriveKnowledgeBaseArticleById(articleId: Number): Observable<any> {
    return this.http.get(this.apiUrl + '/' + articleId).catch(this.handleErrorObservable);
}



/**
    * Handle HTTP error
    */
  private handleErrorObservable (error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.message || error);
  }
}

