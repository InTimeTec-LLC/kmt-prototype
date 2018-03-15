import { Attachement } from './../../shared/modals/knowledge-base-article';
import { Component, OnInit } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Router } from '@angular/router';
import { KnowledgeBaseArticle } from '../../shared/modals/knowledge-base-article';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../shared/service/user/user.service';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { ToasterService } from 'angular5-toaster';
import { environment } from '../../environments/environment';
import { saveAs } from 'file-saver/FileSaver';

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
  file_obj: any;
  attachements: any[] = [];
  quill_config: any;

  constructor(
    private kbContentService: KnowledgeBaseArticleService,
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private auth: AuthenticationService,
    private toasterService: ToasterService
  ) {
    this.quill_config = environment.quillEditorConfig;
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
     attachments: []
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
    const ids = this.attachements.map(obj => {

      return { 'id': obj.id };
    });

    value.attachments = ids;
    this.kbContentService.createKnowledgeBaseArticle(value)
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
  deleteAttachment(attachment_id) {
        if (confirm('Would you like to delete the attachment?')) {
            this.kbContentService.deleteAttachement(attachment_id).subscribe(
                data => {
                    this.updateAttachement(attachment_id);
                    this.toasterService.pop('success', '', data.success.message);
                },
                error => {
                    this.toasterService.pop('error', '', error.success.message);
                });
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
    const files = [].slice.call(event.target.files);
    const fd = new FormData();
    for (const file of files) {
      fd.append('file', file);
    }
    fd.append('fileName', files[0].name);
    fd.append('fileType', files[0].type);
    this.kbContentService.uploadAttachement(fd).subscribe(data => {
        this.attachements.push(data.success.attachement);
        this.toasterService.pop('success', '', data.success.message);
      }, error => {
        this.toasterService.pop('error', '', error.success.message);
      });
  }
  downloadAttachment(id, fileName) {
    this.kbContentService.downloadAttachment(id)
    .subscribe(fileData => saveAs(fileData, fileName));
  }
}
