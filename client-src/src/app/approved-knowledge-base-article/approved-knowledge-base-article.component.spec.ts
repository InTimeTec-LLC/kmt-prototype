import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovedKnowledgeBaseArticleComponent } from './approved-knowledge-base-article.component';
import { QuillModule } from 'ngx-quill';
import { MaterialModule } from '../../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { ToasterService } from 'angular5-toaster';
describe('ApprovedKnowledgeBaseArticleComponent', () => {
  let component: ApprovedKnowledgeBaseArticleComponent;
  let fixture: ComponentFixture<ApprovedKnowledgeBaseArticleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApprovedKnowledgeBaseArticleComponent ],
      providers: [ KnowledgeBaseArticleService, AuthenticationService, ToasterService],
      imports : [ MaterialModule, QuillModule, FormsModule, ReactiveFormsModule, HttpClientModule,HttpModule, RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApprovedKnowledgeBaseArticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
