import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {MaterialModule} from '../../shared/material.module';
import { QuillModule } from 'ngx-quill';
import { ReactiveFormsModule } from '@angular/forms';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { UserService } from '../../shared/service/user/user.service';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { ToasterService } from 'angular5-toaster';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddArticleComponent } from './add.component';


describe('AddArticleComponent', () => {
  let component: AddArticleComponent;
  let fixture: ComponentFixture<AddArticleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers:[KnowledgeBaseArticleService, UserService, AuthenticationService, ToasterService],
      imports:[MaterialModule, QuillModule, ReactiveFormsModule, HttpClientModule, RouterTestingModule, BrowserAnimationsModule],
      declarations: [ AddArticleComponent ]
      
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddArticleComponent);
    component = fixture.componentInstance;
    
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });
});
