import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import {ActivatedRoute} from '@angular/router';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';


@Component({
  selector: 'app-view-knowledge-base-article',
  templateUrl: './view-knowledge-base-article.component.html',
  styleUrls: ['./view-knowledge-base-article.component.scss']
})
export class ViewKnowledgeBaseArticleComponent implements OnInit {

  articleId: any;
  article: any;

  constructor(private kbContentService: KnowledgeBaseArticleService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private auth: AuthenticationService,

  ) {}

  ngOnInit() {
    this.activatedRoute.params.subscribe((params: any) => {
      this.articleId = params['id'];
      if (this.articleId) {
      this.kbContentService.reteriveKnowledgeBaseArticleById(this.articleId).subscribe((data: any) => {
      if (data.hasOwnProperty('article')) {
        data = data.article;
      }
      this.article = data;
      /*this.article.setValue({
      title: data.title,
      description: data.description,
      lastModifiedBy: this.auth.getUserId(),
      approver: data.approver.id,
      articleType: data.articleType.id,
      restricted: data.restricted,
      createdByUser: data.createdBy.firstName + ' ' + data.createdBy.lastName,
      lastModified: data.lastModifiedTime,
      needsApproval: data.needsApproval,
      approved: data.approved
      });*/
      console.log(this.article);
});
}
});
  }

}
