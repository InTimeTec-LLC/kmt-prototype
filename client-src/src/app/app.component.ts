import { Component } from '@angular/core';
import {ToasterModule, ToasterConfig} from 'angular5-toaster';
import { AuthenticationService } from '../shared/service/authentication/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angula5 template editor';
  private toasterconfig: ToasterConfig =
  new ToasterConfig({
      showCloseButton: false,
      tapToDismiss: false,
      timeout: 2000,
      positionClass : 'toast-top-center',
      animate : 'fade'
  });
  constructor(private auth: AuthenticationService) {
  }

  isAuthenticated() {
    return this.auth.isAuthenticated();
  }


}
