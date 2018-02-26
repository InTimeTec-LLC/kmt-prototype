import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import { KnowledgeBaseArticleService } from './service/knowledge-base-article/knowledge-base-article.service';
import { AuthenticationService } from './service/authentication/authentication.service';
import { fakeBackendProvider } from '../mock/fake-backend';
import { AuthGuard } from './service/helper/auth-guards';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './service/helper/jwt-interceptor';
import { UserData } from '../mock/user-data';
import { UserService } from './service/user/user.service';

@NgModule({
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterModule, HttpClientModule],
  declarations: [],
  exports: [CommonModule, FormsModule, RouterModule]
})
export class SharedModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [
        KnowledgeBaseArticleService,
        UserService,
        AuthenticationService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: JwtInterceptor,
            multi: true
        },

        // provider used to create fake backend
        fakeBackendProvider,
        AuthGuard,
        UserData,
        UserService
      ]
    };
  }
}
