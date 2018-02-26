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
import { ArticleData } from './mock-api/article-data';
import { AppRoutingModule } from './app-routing.module';
import { AuthGuard } from '../shared/service/helper/auth-guards';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';



const appRoutes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    data: { title: 'Dashboard' },
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
    data: { title: 'Login' }
  },
  /*{
    path: 'book-edit/:id',
    path: 'books',
    component: BookComponent,
    data: { title: 'Book List' }
  },*/
  // otherwise redirect to home
  { 
    path: '**', 
    redirectTo: 'dashboard' 
  }
];


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LoginComponent,
    AddArticleComponent,
    EditArticleComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    QuillModule,
    HttpClientModule ,
    HttpModule,
    SharedModule.forRoot(),
    InMemoryWebApiModule.forRoot(ArticleData),
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
