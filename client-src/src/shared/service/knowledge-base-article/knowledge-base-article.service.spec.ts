import { TestBed, async } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { KnowledgeBaseArticleService } from './knowledge-base-article.service';

import { Observable } from 'rxjs/Observable';

export function main() {
  describe('Knowledge Base Content Service', () => {

    let kbContentService: KnowledgeBaseArticleService;
    let httpMock: HttpTestingController;

    beforeEach(() => {

      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [KnowledgeBaseArticleService]
      });

      kbContentService = TestBed.get(KnowledgeBaseArticleService);
      httpMock = TestBed.get(HttpTestingController);
    });

    it('should return an Observable when get called', async(() => {
      expect(TestBed.get(KnowledgeBaseArticleService).get()).toEqual(jasmine.any(Observable));
    }));

    it('should resolve to list of knowledge base content when get called', async(() => {

      const expectedKnowledgeBaseContent = [
                {"id":1, "title":"Test Title1", "description":"Test Description1"}, 
                {"id":2, "title":"Test Title2", "description":"Test Description2"},
                {"id":3, "title":"Test Title3", "description":"Test Description3"},
                {"id":4, "title":"Test Title4", "description":"Test Description4"},
                {"id":5, "title":"Test Title5", "description":"Test Description5"}

      ];

      let actualKnowledgeBaseContent: Object[] = [];
      kbContentService.listKnowledgeBaseArticle().subscribe((kb: string[]) => {
        actualKnowledgeBaseContent = kb;
      });

      httpMock.expectOne('assets/data.json').flush(expectedKnowledgeBaseContent);

      expect(actualKnowledgeBaseContent).toEqual(expectedKnowledgeBaseContent);
    }));
  });
}
