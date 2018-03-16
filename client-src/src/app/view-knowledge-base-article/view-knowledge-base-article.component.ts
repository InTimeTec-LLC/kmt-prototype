import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import {ActivatedRoute} from '@angular/router';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { saveAs } from 'file-saver/FileSaver';
import { Http, Headers } from '@angular/http';


@Component({
  selector: 'app-view-knowledge-base-article',
  templateUrl: './view-knowledge-base-article.component.html',
  styleUrls: ['./view-knowledge-base-article.component.scss']
})
export class ViewKnowledgeBaseArticleComponent implements OnInit {

  articleId: any;
  article: any;
  attachements: any[] = [];
  currentUserId: any;
  currentUserRole: any;


  constructor(private kbContentService: KnowledgeBaseArticleService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private auth: AuthenticationService,

  ) {
    this.currentUserId = this.auth.getUserId();
    this.currentUserRole = this.auth.getUserRole();
  }

  onTapNavigation(route, param) {
      if (param) {
          this.router.navigate([route, param], {relativeTo: this.activatedRoute} );
      } else {
          this.router.navigate([route], {relativeTo: this.activatedRoute} );
      }
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe((params: any) => {
      this.articleId = params['id'];
      if (this.articleId) {
      this.kbContentService.reteriveKnowledgeBaseArticleById(this.articleId).subscribe((data: any) => {
          if (data.hasOwnProperty('article')) {
            data = data.article;
            this.attachements = data.attachments;
          }
          this.article = data;
         });
      }
    });
  }

  downloadAttachment(id, fileName) {
    this.kbContentService.downloadAttachment(id)
    .subscribe(fileData => saveAs(fileData, fileName));
  }
}
