import { TestBed, async } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { KnowledgeBaseArticleService } from './knowledge-base-article.service';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../../environments/environment';
import { Http, HttpModule } from '@angular/http';

let articleTypeURL = environment.API_ENDPOINT + 'articles/types';


  describe('KnowledgeBaseArticleService', () => {

    const mockArticleResponse = {
      "types": [
          {
              "id": "11",
              "type": "string"
          }
      ]
  };
    
    let kbContentService: KnowledgeBaseArticleService;
    let httpMock: HttpTestingController;

    beforeEach(() => {

      TestBed.configureTestingModule({
        imports: [HttpModule , HttpClientTestingModule],
        providers: [KnowledgeBaseArticleService]
      });

      kbContentService = TestBed.get(KnowledgeBaseArticleService);
      httpMock = TestBed.get(HttpTestingController);
    });

    it('ListKBArticles', async(() => {

      let actualKnowledgeBaseContent: object[] = [];
      kbContentService.listKnowledgeBaseArticleTypes().subscribe((kb: object[]) => {
        actualKnowledgeBaseContent = kb;
      });

      const req = httpMock.expectOne(articleTypeURL);
      expect(req.request.method).toBe('GET');
      req.flush(mockArticleResponse);
      
    }));
  });

