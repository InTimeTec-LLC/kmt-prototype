import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import { KnowledgeBaseArticle , UpdateKnowledgeBaseArticle} from '../../shared/modals/knowledge-base-article';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../shared/service/user/user.service';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { ToasterService } from 'angular5-toaster';
import {ActivatedRoute} from '@angular/router';
import { environment } from '../../environments/environment';
import { saveAs } from 'file-saver/FileSaver';
import { Http, Headers } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { DatePipe } from '@angular/common';

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
  attachements: any[] = [];
  quill_config: any;
  comments: any[] = [];
  fileCount: number;
  fileError: boolean;
  errorMsg: string;

  constructor(
    private kbContentService: KnowledgeBaseArticleService,
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private auth: AuthenticationService,
    private toasterService: ToasterService,
    private activatedRoute: ActivatedRoute,
    private http: Http,
      ) {

    this.fileCount = 0;
    this.fileError = false;
    this.errorMsg = '';
    this.quill_config = environment.quillEditorConfig;
    this.activatedRoute.params.subscribe((params: any) => {
      this.articleId = params['id'];
      if (this.articleId) {
        this.kbContentService.reteriveKnowledgeBaseArticleById(this.articleId).subscribe((data: any) => {
          if (data.hasOwnProperty('article')) {
            data = data.article;
          }
          const pipe = new DatePipe('en-US');
          this.attachements = data.attachments;
          this.fileCount = data.attachments.length;
          this.comments = data.comments;
          this.article.setValue({
            title: data.title,
            description: data.description,
            lastModifiedBy: this.auth.getUserId(),
            approver: data.approver.id,
            articleType: data.articleType.id,
            restricted: data.restricted,
            createdByUser: data.createdBy.firstName + ' ' + data.createdBy.lastName,
            lastModified: pipe.transform(data.lastModifiedTime, 'MM/dd/yyyy hh:mm a'),
            needsApproval: data.needsApproval,
            attachments: []

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
     needsApproval: '',
     attachments: []
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
    const ids = this.attachements.map(obj => {
      return { 'id': obj.id };
    });

    value.attachments = ids;
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
    this.router.navigateByUrl('/articles');
  }

  validateFile() {
    if (this.fileCount > 19) {
      this.fileError = true;
      this.errorMsg = 'File attachment limit exceeded';
      return false;
    } else {
      this.fileError = false;
      this.errorMsg = '';
      return true;
    }
  }

  updateAttachement(id) {
    for (let i = 0; i < this.attachements.length; i++) {
        const obj = this.attachements[i];

        if (obj.id === id) {
          this.attachements.splice(i, 1);
        }
    }
  }

  onChange(event: any) {
    if (this.validateFile() !== true) {
      return false;
    }

    const files = [].slice.call(event.target.files);
    const fd = new FormData();
    for (const file of files) {
      fd.append('file', file);
    }
    fd.append('fileName', files[0].name);
    fd.append('fileType', files[0].type);
    this.kbContentService.uploadAttachement(fd).subscribe(data => {
        this.attachements.push(data.success.attachement);
        this.fileCount = this.fileCount + 1;
        this.toasterService.pop('success', '', data.success.message);
      }, error => {
        this.toasterService.pop('error', '', error.success.message);
      });
  }

  deleteAttachment(attachment_id) {
        if (confirm('Would you like to delete existing attachment?')) {
            this.kbContentService.deleteAttachement(attachment_id).subscribe(
                data => {
                    this.updateAttachement(attachment_id);
                    this.fileCount = this.fileCount - 1;
                    this.toasterService.pop('success', '', data.success.message);
                },
                error => {
                    this.toasterService.pop('error', '', error.success.message);
                });
            }
  }

  downloadAttachment(id, fileName) {
    this.kbContentService.downloadAttachment(id)
    .subscribe(fileData => saveAs(fileData, fileName));
  }
}
