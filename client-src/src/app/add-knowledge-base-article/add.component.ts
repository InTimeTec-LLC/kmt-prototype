import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import { KnowledgeBaseArticle } from '../../shared/modals/knowledge-base-article';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-kb-article',
  templateUrl: './add.component.html',
  styleUrls: []
})
export class AddArticleComponent implements OnInit {

  articles: KnowledgeBaseArticle[];
  errorMessage: String;
  articleTitle: String;
  article: FormGroup;



  constructor(
    private kbContentService: KnowledgeBaseArticleService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit() {
    this.article = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(2)]],
     description: ['', Validators.required],
    });

  }


  onSubmit({value, valid}: {value: KnowledgeBaseArticle, valid: boolean }) {
    this.kbContentService.createKnowledgeBaseArticle(value)
    .subscribe( article => {
                    this.articleTitle = article.title;
         },
                  error => this.errorMessage = <any>error);

  }


}
