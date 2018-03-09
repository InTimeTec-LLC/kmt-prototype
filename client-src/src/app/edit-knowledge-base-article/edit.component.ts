import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import { KnowledgeBaseArticle , UpdateKnowledgeBaseArticle} from '../../shared/modals/knowledge-base-article';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../shared/service/user/user.service';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { ToasterService } from 'angular5-toaster';
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
          console.log(data.createdBy.firstName + ' ' + data.createdBy.lastName);
          this.article.setValue({
            title: data.title,
            description: data.description,
            lastModifiedBy: this.auth.getUserId(),
            approver: data.approver.id,
            articleType: data.articleType.id,
            restricted: data.restricted,
            createdByUser: data.createdBy.firstName + ' ' + data.createdBy.lastName,
            lastModified: data.lastModifiedTime,
            needsApproval: data.needsApproval

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
     lastModifiedBy: '',
     restricted: '',
     createdByUser: '',
     lastModified: '',
     needsApproval: ''
    });

    this.kbContentService.listKnowledgeBaseArticleTypes().subscribe((data: any) => {
        this.types = data.types;
    });

    this.userService.listApprovers().subscribe((data: any) => {
      this.approvers = data.users;
    });

  }


  onSubmit({value, valid}: {value: any, valid: boolean }) {
    delete value.createdByUser;
    delete value.lastModified;
    value = <UpdateKnowledgeBaseArticle> value;
    this.kbContentService.updateKnowledgeBaseArticle(this.articleId, value)
    .subscribe( data => {
          this.toasterService.pop('success', '', data.success.message);
          this.onCancle();
          },
          error => {
              this.toasterService.pop('error', '', error.error.success.message);
          }
    );

  }

  onCancle() {
    this.router.navigateByUrl('/articlelist');
  }


}
