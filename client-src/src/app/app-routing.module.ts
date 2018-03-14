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
import { RootComponent } from './root.component';

const routes:  Routes = [
    {
      path: '',
      component: RootComponent,
      children : [
        {
          path: '',
          component: DashboardComponent,
          data: { title: 'Dashboard'},
          canActivate: [AuthGuard],
        },
        {
          path: 'login',
          component: LoginComponent,
          data: { title: 'Login' }
        },
        {
          path: 'knowledge-base',
          component: SearchKnowledgeBaseComponent,
          data: { title: 'Knowledge Base', breadcrumb: 'Knowledge Base' }
        },
        {
          path: 'knowledge-base-search',
          component: KnowledgeBaseSearchComponent,
          data: { title: 'Knowledge Base Search', breadcrumb: 'Knowledge Base Search' }
        },
        {
          path: 'users',
          component: UserListComponent,
          data: { breadcrumb: 'Users' },
          runGuardsAndResolvers: 'always',
          children : [
            { path: 'edit/:id',
              component: EditUserComponent,
              data: { breadcrumb: 'Edit User' }
            },
            { path: 'add',
              component: AddUserComponent,
              data: { breadcrumb: 'Add User' }
            }
          ]
        },
        { path: 'articles',
          component: ListArticleComponent,
          data: { breadcrumb: 'Articles' },
          children : [
            {
              path: 'edit/:id',
              component: EditArticleComponent,
              data: { breadcrumb: 'Edit Article' }
            },
            {
              path: 'add',
              component: AddArticleComponent,
              data: { breadcrumb: 'Add Article' }
            },
            {
              path: 'detail/:id',
              component: ViewKnowledgeBaseArticleComponent,
              data: { breadcrumb: 'Article Detail' }
            },
            {
              path: 'approved-view/:id',
              component: ApprovedKnowledgeBaseArticleComponent,
              data: { breadcrumb: 'Approved Articles' }
            }
          ]
        }
      ]
    }
  ];


@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload', enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
