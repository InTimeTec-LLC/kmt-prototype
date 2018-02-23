import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { KnowledgeBaseContentService } from './service/knowledge-base-content/knowledge-base.service';
import { AuthenticationService } from './service/authentication/authentication.service';
import { fakeBackendProvider } from './service/helper/fake-backend';
import { AuthGuard } from './service/helper/auth-guards';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './service/helper/jwt-interceptor';


@NgModule({
  imports: [CommonModule, RouterModule],
  declarations: [],
  exports: [CommonModule, FormsModule, RouterModule]
})
export class SharedModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [
        KnowledgeBaseContentService, 
        AuthenticationService, 
        {
            provide: HTTP_INTERCEPTORS,
            useClass: JwtInterceptor,
            multi: true
        },

        // provider used to create fake backend
        fakeBackendProvider,
        AuthGuard
      ]
    };
  }
}
