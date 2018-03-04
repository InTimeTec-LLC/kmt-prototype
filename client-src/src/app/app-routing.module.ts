import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddArticleComponent } from './add-knowledge-base-article/add.component';
import { EditArticleComponent } from './edit-knowledge-base-article/edit.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from '../shared/service/helper/auth-guards';
import { AddUserComponent } from './add-user/add-user.component';
import { UserListComponent } from './user-list/user-list.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { ViewKnowledgeBaseArticleComponent } from './view-knowledge-base-article/view-knowledge-base-article.component';

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
    {
      path: 'article/add',
      component: AddArticleComponent,
      data: { title: 'Add Article' }
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
    { path: 'article-detail/:id',
      component: ViewKnowledgeBaseArticleComponent,
      data: { title: 'Article Detail' }
    },
  ];


@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
