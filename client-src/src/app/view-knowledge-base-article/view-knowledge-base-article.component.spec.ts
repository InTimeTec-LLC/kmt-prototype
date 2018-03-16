import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../shared/material.module';
import { QuillModule } from 'ngx-quill';
import { ReactiveFormsModule } from '@angular/forms';
import { ViewKnowledgeBaseArticleComponent } from './view-knowledge-base-article.component';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';

describe('ViewKnowledgeBaseArticleComponent', () => {
  let component: ViewKnowledgeBaseArticleComponent;
  let fixture: ComponentFixture<ViewKnowledgeBaseArticleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewKnowledgeBaseArticleComponent ],
      imports: [ MaterialModule, QuillModule, FormsModule, ReactiveFormsModule, HttpClientModule, HttpModule, RouterTestingModule],
      providers:[ KnowledgeBaseArticleService, AuthenticationService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewKnowledgeBaseArticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
