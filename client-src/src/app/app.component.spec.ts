import { TestBed, async } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LeftPanelComponent } from './left-panel/left-panel.component';
import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from '../shared/material.module';
import {ToasterModule, ToasterService} from 'angular5-toaster';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddArticleComponent } from './add-knowledge-base-article/add.component';
import { ListArticleComponent } from './list-knowledge-base-article/list-article.component';
import { ViewKnowledgeBaseArticleComponent } from './view-knowledge-base-article/view-knowledge-base-article.component';
import { EditArticleComponent } from './edit-knowledge-base-article/edit.component';
import { AddUserComponent } from './add-user/add-user.component';
import { UserListComponent } from './user-list/user-list.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { ApprovedKnowledgeBaseArticleComponent } from './approved-knowledge-base-article/approved-knowledge-base-article.component';
import { SearchKnowledgeBaseComponent } from './search-knowledge-base/search-knowledge-base.component';
import { FooterComponent } from './footer/footer.component';
import { QuillModule } from 'ngx-quill';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationService } from '../shared/service/authentication/authentication.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MessageService } from '../shared/service/message/message';
describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports:[FormsModule,QuillModule, AppRoutingModule,ReactiveFormsModule, MaterialModule, ToasterModule, Ng4LoadingSpinnerModule.forRoot(), 
        RouterTestingModule, HttpClientModule, BrowserAnimationsModule ],
      declarations: [
        AppComponent, LeftPanelComponent, LoginComponent, DashboardComponent,
        ListArticleComponent, AddArticleComponent, ViewKnowledgeBaseArticleComponent,
        EditArticleComponent, AddUserComponent, UserListComponent,EditUserComponent,
        ApprovedKnowledgeBaseArticleComponent, SearchKnowledgeBaseComponent, FooterComponent,
        
      ],
      providers: [ AuthenticationService, MessageService]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
