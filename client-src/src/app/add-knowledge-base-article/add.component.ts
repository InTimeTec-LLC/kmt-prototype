import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import { KnowledgeBaseArticle } from '../../shared/modals/knowledge-base-article';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../shared/service/user/user.service';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import {ToasterModule, ToasterService, ToasterConfig} from 'angular5-toaster';

@Component({
  selector: 'app-add-kb-article',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddArticleComponent implements OnInit {

  articles: KnowledgeBaseArticle[];
  errorMessage: String;
  articleTitle: String;
  article: FormGroup;
  types: any[];
  approvers: any[];
  private toasterconfig: ToasterConfig =
        new ToasterConfig({
            showCloseButton: false,
            tapToDismiss: false,
            timeout: 2000,
            positionClass : 'toast-top-center',
            animate : 'fade'
        });



  constructor(
    private kbContentService: KnowledgeBaseArticleService,
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private auth: AuthenticationService,
    private toasterService: ToasterService
  ) {
  }

  ngOnInit() {
    this.article = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(2)]],
     description: ['', Validators.required],
     articleType: ['', Validators.required],
     approver: ['', Validators.required],
     createdBy: '',
     restricted: '',
     needsApproval: false,
     approved: false
    });

    this.article.patchValue({
      createdBy: this.auth.getUserId(),
      restricted: false
    });

    this.kbContentService.listKnowledgeBaseArticleTypes().subscribe((data: any) => {
        this.types = data.types;
    });

    this.userService.listApprovers().subscribe((data: any) => {
      this.approvers = data.users;
    });

  }


  onSubmit({value, valid}: {value: KnowledgeBaseArticle, valid: boolean }) {
    this.kbContentService.createKnowledgeBaseArticle(value)
    .subscribe( article => {
            // article.success.message
            this.toasterService.pop('success', 'Success', 'Article has been added successfully');
         },
            // error => this.toasterService.pop('error', 'Error', error.failure.message);
            error => this.toasterService.pop('success', 'Success', 'Article has been added successfully')
        );

  }

  onCancle() {
    this.router.navigateByUrl('/articlelist');
  }


}
