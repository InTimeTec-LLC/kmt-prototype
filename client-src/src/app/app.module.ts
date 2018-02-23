import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { QuillEditorSampleComponent } from './quill-editor-sample/quill-editor-sample.component';
import { QuillModule } from 'ngx-quill';
import { SharedModule } from '../shared/shared.module';
import { LoginComponent } from './login/login.component';
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
    QuillEditorSampleComponent,
    DashboardComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    QuillModule,
    HttpClientModule ,
    HttpModule,
    SharedModule.forRoot(),
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    )

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
