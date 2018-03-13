import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddArticleComponent } from './add-knowledge-base-article/add.component';
import { EditArticleComponent } from './edit-knowledge-base-article/edit.component';
import { ViewKnowledgeBaseArticleComponent } from './view-knowledge-base-article/view-knowledge-base-article.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from '../shared/service/helper/auth-guards';
import { AddUserComponent } from './add-user/add-user.component';
import { UserListComponent } from './user-list/user-list.component';
import { ListArticleComponent } from './list-knowledge-base-article/list-article.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { ApprovedKnowledgeBaseArticleComponent } from './approved-knowledge-base-article/approved-knowledge-base-article.component';
import { SearchKnowledgeBaseComponent } from './search-knowledge-base/search-knowledge-base.component';
import { KnowledgeBaseSearchComponent } from './knowledge-base-search/knowledge-base-search.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';

const routes:  Routes = [
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
    { path: 'articlelist',
      component: ListArticleComponent,
      data: { title: 'Article List' }
    },
    {
      path: 'article/add',
      component: AddArticleComponent,
      data: { title: 'Add Article' }
    },
    {
      path: 'article-detail/:id',
      component: ViewKnowledgeBaseArticleComponent,
      data: { title: 'Articles' }
    },
    {
      path: 'article/edit/:id',
      component: EditArticleComponent,
      data: { title: 'Edit Article' }
    },
    { path: 'user/add',
      component: AddUserComponent,
      data: { title: 'Add User' }
    },
    { path: 'userlist',
      component: UserListComponent,
      data: { title: 'User List' }
    },
    { path: '',
      redirectTo: '/dashboard',
      pathMatch: 'full'
    },
    { path: 'user/edit/:id',
      component: EditUserComponent,
      data: { title: 'Edit User' }
    },
    {
      path: 'article-approved-view/:id',
      component: ApprovedKnowledgeBaseArticleComponent,
      data: { title: 'Approved Articles' }
    },
    {
      path: 'knowledge-base',
      component: SearchKnowledgeBaseComponent,
      data: { title: 'Knowledge Base' }
    },
    {
      path: 'knowledge-base-search',
      component: KnowledgeBaseSearchComponent,
      data: { title: 'Knowledge Base Search' }
    },
    {
      path: 'forgot-password',
      component: ForgotPasswordComponent,
      data: { title: 'Forgot Password' }
    },
  ];


@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
