import { Component } from '@angular/core';
import {ToasterModule, ToasterConfig} from 'angular5-toaster';


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
  constructor() {
  }
}
