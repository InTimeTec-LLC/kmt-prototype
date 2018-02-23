import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import { KnowledgeBaseArticle } from '../../shared/modals/knowledge-base-article';

@Component({
  selector: 'app-add-kb-article',
  templateUrl: './add.component.html',
  styleUrls: []
})
export class AddArticleComponent implements OnInit {

  articles: KnowledgeBaseArticle[];
  errorMessage: String;
  articleTitle: String;
  article = new KnowledgeBaseArticle();


  constructor(
    private kbContentService: KnowledgeBaseArticleService
  ) {
  }

  ngOnInit() {
  }


  addArticle() {
    this.kbContentService.createKnowledgeBaseArticle(this.article)
    .subscribe( article => {
                    this.reset();
                    this.articleTitle = article.title;
         },
                  error => this.errorMessage = <any>error);

  }

  private reset() {
    this.article.id = null;
    this.article.title = null;
    this.article.description = null;
    this.errorMessage = null;
    this.articleTitle = null;
  }

}
