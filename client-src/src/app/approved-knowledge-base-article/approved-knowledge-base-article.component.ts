import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import {ActivatedRoute} from '@angular/router';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { ApprovedKnowledgeBaseArticle } from '../../shared/modals/knowledge-base-article';
import { ToasterService } from 'angular5-toaster';
import { saveAs } from 'file-saver/FileSaver';
import { Http, Headers } from '@angular/http';

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
  attachements: any[] = [];
  comments: any[] = [];
  error: boolean;


  constructor(private kbContentService: KnowledgeBaseArticleService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private auth: AuthenticationService,
              private toasterService: ToasterService
  ) {
    this.error = false;
    this.activatedRoute.params.subscribe((params: any) => {
      this.articleId = params['id'];
      if (this.articleId) {
      this.kbContentService.reteriveKnowledgeBaseArticleById(this.articleId).subscribe((data: any) => {
      if (data.hasOwnProperty('article')) {
        data = data.article;
      }
      this.article = data;
      this.attachements = data.attachments;
      this.comments = this.article.comments;

    });
    }
} );

  }

  ngOnInit() {
  }

  valuechange(newValue) {
    if ( newValue !== undefined &&  newValue !== '') {
      this.error = false;
   } else {
      this.error = true;
   }
}

  onCancle() {
    this.submit({'comment': this.comment, 'approved': false});
  }

  submit(articleInfo: ApprovedKnowledgeBaseArticle) {
    this.kbContentService.approvedKnowledgeBaseArticle(this.articleId, articleInfo)
    .subscribe( article => {
            // article.success.message
              this.toasterService.pop('success', 'Success', article.success.message);
              this.router.navigateByUrl('/articles');
         },
              // error.failure.message
               error => this.toasterService.pop('error', 'Error', error.failure.message)
        );
  }

  onReviewComments() {
    if ( this.comment !== undefined) {
      this.submit({'comment': this.comment, 'approved': false});
    } else {
      this.error = true;
    }
  }
  onApproved() {
    this.submit({'comment': this.comment, 'approved': true});
  }

  downloadAttachment(id, fileName) {
    this.kbContentService.downloadAttachment(id)
    .subscribe(fileData => saveAs(fileData, fileName));
  }

}
