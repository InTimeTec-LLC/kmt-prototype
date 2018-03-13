// modules & services
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { QuillModule } from 'ngx-quill';
import { SharedModule } from '../shared/shared.module';
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { ArticleData } from '../mock/article-data';
import { AppRoutingModule } from './app-routing.module';
import { AuthGuard } from '../shared/service/helper/auth-guards';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { MaterialModule } from '../shared/material.module';
import {ToasterModule, ToasterService} from 'angular5-toaster';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

// components
import { LoginComponent } from './login/login.component';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddArticleComponent } from './add-knowledge-base-article/add.component';
import { EditArticleComponent } from './edit-knowledge-base-article/edit.component';
import { AddUserComponent } from './add-user/add-user.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { ViewKnowledgeBaseArticleComponent } from './view-knowledge-base-article/view-knowledge-base-article.component';
import { ApprovedKnowledgeBaseArticleComponent } from './approved-knowledge-base-article/approved-knowledge-base-article.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainComponent } from './main/main.component';
import { LeftPanelComponent } from './left-panel/left-panel.component';
import { FooterComponent } from './footer/footer.component';
import { ListArticleComponent, ArticleListFilterComponent } from './list-knowledge-base-article/list-article.component';
import { UserListComponent, UserListFilterComponent } from './user-list/user-list.component';
import { SearchKnowledgeBaseComponent } from './search-knowledge-base/search-knowledge-base.component';
import { KnowledgeBaseSearchComponent } from './knowledge-base-search/knowledge-base-search.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LoginComponent,
    AddArticleComponent,
    EditArticleComponent,
    AddUserComponent,
    FooterComponent,
    LeftPanelComponent,
    ListArticleComponent,
    MainComponent,
    UserListComponent,
    EditUserComponent,
    UserListFilterComponent,
    ArticleListFilterComponent,
    ViewKnowledgeBaseArticleComponent,
    ApprovedKnowledgeBaseArticleComponent,
    SearchKnowledgeBaseComponent,
    KnowledgeBaseSearchComponent,
    ForgotPasswordComponent
  ], 
  imports: [
    BrowserModule,
    FormsModule,
    QuillModule,
    HttpClientModule ,
    HttpModule,
    SharedModule.forRoot(),
    InMemoryWebApiModule.forRoot(ArticleData, {
      passThruUnknownUrl: true
    }),
    AppRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToasterModule,
    Ng4LoadingSpinnerModule.forRoot()

  ],
  entryComponents: [ArticleListFilterComponent, UserListFilterComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
