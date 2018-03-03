import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { QuillModule } from 'ngx-quill';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddArticleComponent } from './add-knowledge-base-article/add.component';
import { EditArticleComponent } from './edit-knowledge-base-article/edit.component';
import { SharedModule } from '../shared/shared.module';
import { LoginComponent } from './login/login.component';
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { ArticleData } from '../mock/article-data';
import { AppRoutingModule } from './app-routing.module';
import { AuthGuard } from '../shared/service/helper/auth-guards';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { AddUserComponent } from './add-user/add-user.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainComponent } from './main/main.component';
import { LeftPanelComponent } from './left-panel/left-panel.component';
import { FooterComponent } from './footer/footer.component';
import { ContentComponent } from './content/content.component';
import { UserListComponent } from './user-list/user-list.component';
import { MaterialModule } from '../shared/material.module';
import {ToasterModule, ToasterService} from 'angular5-toaster';


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
    ContentComponent,
    MainComponent,
    UserListComponent,
    EditUserComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    QuillModule,
    HttpClientModule ,
    HttpModule,
    SharedModule.forRoot(),
    InMemoryWebApiModule.forRoot(ArticleData, {
      passThruUnknownUrl: false
    }),
    AppRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToasterModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
