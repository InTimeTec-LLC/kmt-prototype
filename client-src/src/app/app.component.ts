import { Component, OnInit, ViewChild, ElementRef, AfterViewInit  } from '@angular/core';
import {ToasterModule, ToasterConfig} from 'angular5-toaster';
import { AuthenticationService } from '../shared/service/authentication/authentication.service';
import { MessageService } from '../shared/service/message/message';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {
  title = 'angula5 template editor';
  loggeInStatus: boolean;
  @ViewChild('drawer') drawer: any;

  private toasterconfig: ToasterConfig =
  new ToasterConfig({
      showCloseButton: false,
      tapToDismiss: false,
      timeout: 3000,
      positionClass : 'toast-top-center',
      animate : 'fade'
  });
  constructor(private auth: AuthenticationService, private messageService: MessageService) {
  }

  isAuthenticated() {
    this.auth.isAuthenticated().subscribe(status => this.loggeInStatus = status );
    return this.loggeInStatus;
  }

  ngOnInit() {
    this.isAuthenticated();
 }

 ngAfterViewInit() {
  this.getMatDrawerStatus();
 }

 getMatDrawerStatus() {
  this.messageService.getMessage().subscribe(message => {
    if (message.text === 'closeMatDrawer') {
      this.drawer.opened = false;
    } else if (message.text === 'openMatDrawer') {
      this.drawer.opened = true;
    }
 });
 }



}
