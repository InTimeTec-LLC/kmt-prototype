import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import { KnowledgeBaseArticle } from '../../shared/modals/knowledge-base-article';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-edit-kb-article',
  templateUrl: './edit.component.html',
  styleUrls: []
})
export class EditArticleComponent implements OnInit {

  articles: KnowledgeBaseArticle[];
  errorMessage: String;
  articleTitle: String;
  article: FormGroup;
  articleId: Number;


  constructor(
    private kbContentService: KnowledgeBaseArticleService,
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute
  ) {
    this.activatedRoute.params.subscribe((params: any) => {
      this.articleId = params['id'];
      if (this.articleId) {
        this.kbContentService.reteriveKnowledgeBaseArticleById(this.articleId).subscribe((article: KnowledgeBaseArticle) => {
          this.article.setValue({
            title: article.title,
            description: article.description
          });
        });
      }
    });

  }

  ngOnInit() {
    this.article = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(2)]],
     description: ['', Validators.required],
    });

  }


  onSubmit({value, valid}: {value: KnowledgeBaseArticle, valid: boolean }) {
    this.kbContentService.updateKnowledgeBaseArticle(this.articleId, value)
    .subscribe( article => {
                    this.articleTitle = article.title;
         },
                  error => this.errorMessage = <any>error);

  }
}
