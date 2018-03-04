import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import { KnowledgeBaseArticle } from '../../shared/modals/knowledge-base-article';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../shared/service/user/user.service';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import {ToasterModule, ToasterService} from 'angular5-toaster';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-edit-kb-article',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditArticleComponent implements OnInit {

  articles: KnowledgeBaseArticle[];
  errorMessage: String;
  articleTitle: String;
  article: FormGroup;
  types: any[];
  approvers: any[];
  articleId: any;



  constructor(
    private kbContentService: KnowledgeBaseArticleService,
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private auth: AuthenticationService,
    private toasterService: ToasterService,
    private activatedRoute: ActivatedRoute

  ) {
    this.activatedRoute.params.subscribe((params: any) => {
      this.articleId = params['id'];
      if (this.articleId) {
        this.kbContentService.reteriveKnowledgeBaseArticleById(this.articleId).subscribe((data: any) => {
          if (data.hasOwnProperty('article')) {
            data = data.article;
          }
          console.log(data);
          this.article.setValue({
            title: data.title,
            description: data.description,
            createdBy: data.createdBy.id,
            approver: data.approver.id,
            articleType: data.articleType.id,
            restricted: data.restricted,
            createdByUser: '',
            lastModified: data.lastModifiedTime,

          });
        });
      }
    });

  }

  ngOnInit() {

    this.article = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(2)]],
     description: ['', Validators.required],
     articleType: ['', Validators.required],
     approver: ['', Validators.required],
     createdBy: '',
     restricted: '',
     createdByUser: '',
     lastModified: ''
    });

    this.article.patchValue({
      createdBy: this.auth.getUserId(),
      restricted: false,
      createdByUser: this.auth.getUserName()
    });

    this.kbContentService.listKnowledgeBaseArticleTypes().subscribe((data: any) => {
        this.types = data.types;
    });

    this.userService.listApprovers().subscribe((data: any) => {
      this.approvers = data.users;
    });

  }


  onSubmit({value, valid}: {value: KnowledgeBaseArticle, valid: boolean }) {
    this.kbContentService.updateKnowledgeBaseArticle(this.articleId, value)
    .subscribe( article => {
            // article.success.message
            this.toasterService.pop('success', 'Success', 'Modifications have been saved successfully');
         },
                error => this.toasterService.pop('error', 'Error', error.failure.message)
        );

  }

  onCancle() {
    this.router.navigateByUrl('/article-list');
  }


}
