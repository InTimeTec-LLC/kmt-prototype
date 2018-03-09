import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import {ActivatedRoute} from '@angular/router';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { ApprovedKnowledgeBaseArticle } from '../../shared/modals/knowledge-base-article';
import { ToasterService } from 'angular5-toaster';

@Component({
  selector: 'app-approved-knowledge-base-article',
  templateUrl: './approved-knowledge-base-article.component.html',
  styleUrls: ['./approved-knowledge-base-article.component.css']
})
export class ApprovedKnowledgeBaseArticleComponent implements OnInit {

  articleId: any;
  article: any;
  comment: any;
  articleInfo: any;

  constructor(private kbContentService: KnowledgeBaseArticleService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private auth: AuthenticationService,
              private toasterService: ToasterService
  ) {
    this.activatedRoute.params.subscribe((params: any) => {
      this.articleId = params['id'];
      if (this.articleId) {
      this.kbContentService.reteriveKnowledgeBaseArticleById(this.articleId).subscribe((data: any) => {
      if (data.hasOwnProperty('article')) {
        data = data.article;
      }
      this.article = data;
    });
    }
} );

  }

  ngOnInit() {

  }

  onCancle() {
    this.submit({'comment': this.comment, 'approved': false});
  }

  submit(articleInfo: ApprovedKnowledgeBaseArticle) {
    this.kbContentService.approvedKnowledgeBaseArticle(this.articleId, articleInfo)
    .subscribe( article => {
            // article.success.message
              this.toasterService.pop('success', 'Success', article.success.message);
              this.router.navigateByUrl('/articlelist');
         },
              // error.failure.message
               error => this.toasterService.pop('error', 'Error', error.failure.message)
        );
  }

  onReviewComments() {
    this.submit({'comment': this.comment, 'approved': false});
  }

  onApproved() {
    this.submit({'comment': this.comment, 'approved': true});
  }

}
