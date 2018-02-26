import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddArticleComponent } from './add-knowledge-base-article/add.component';
import { EditArticleComponent } from './edit-knowledge-base-article/edit.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from '../shared/service/helper/auth-guards';
import { AddUserComponent } from './add-user/add-user.component';

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
      data: { title: 'Add knowledge Base Article' }
    },
    {
      path: 'article/edit/:id',
      component: EditArticleComponent,
      data: { title: 'Edit Knowledge Base Article' }
    },
    { path: 'user/add',
      component: AddUserComponent,
      data: { title: 'Add User' }
    },
    { path: '',
      redirectTo: '/dashboard',
      pathMatch: 'full'
    }
  ];


@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
