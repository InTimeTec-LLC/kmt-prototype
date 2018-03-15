import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditArticleComponent } from './edit.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../shared/material.module';
import { QuillModule } from 'ngx-quill';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { UserService } from '../../shared/service/user/user.service';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { ToasterService } from 'angular5-toaster';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
describe('AddArticleComponent', () => {
  let component: EditArticleComponent;
  let fixture: ComponentFixture<EditArticleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditArticleComponent ],
      imports:[ MaterialModule, QuillModule, FormsModule, ReactiveFormsModule, HttpClientModule, RouterTestingModule, BrowserAnimationsModule],
      providers:[ KnowledgeBaseArticleService, UserService, AuthenticationService, ToasterService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditArticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
