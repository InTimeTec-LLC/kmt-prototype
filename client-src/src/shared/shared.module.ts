import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import { KnowledgeBaseArticleService } from './service/knowledge-base-article/knowledge-base-article.service';
import { CustomQuillEditorComponent } from './component/quill-editor/quill-editor.component';
import { QuillModule } from 'ngx-quill';

@NgModule({
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule, HttpClientModule, QuillModule],
  declarations: [CustomQuillEditorComponent],
  exports: [CommonModule, FormsModule, RouterModule]
})
export class SharedModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [KnowledgeBaseArticleService]
    };
  }
}
