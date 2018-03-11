import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { MessageService } from '../../shared/service/message/message';


@Component({
  selector: 'app-left-panel',
  templateUrl: './left-panel.component.html',
  styleUrls: ['./left-panel.component.scss']
})
export class LeftPanelComponent implements OnInit, OnDestroy {

  userType: any;
  subscription: Subscription;
  loggeInStatus: boolean;

  constructor(private auth: AuthenticationService, private router: Router, private messageService: MessageService) { 
     this.userType = this.auth.getUserType();
     this.subscription = this.messageService.getMessage().subscribe(message => {
        this.userType = this.auth.getUserType();
     });
  }

  ngOnDestroy() {
      // unsubscribe to ensure no memory leaks
      this.subscription.unsubscribe();
  }

  ngOnInit() {
    this.isAuthenticated();
  }

  refreshUserType() {
    this.userType = this.auth.getUserType();
  }

  onTapNavigation(route) {
    this.router.navigate([route]);
  }

  isAuthenticated() {
    this.auth.isAuthenticated().subscribe(status => this.loggeInStatus = status );
    return this.loggeInStatus;
  }

  getUserName() {
    return this.auth.getUserName();
  }

  logout() {
    this.messageService.sendMessage('closeMatDrawer');
    this.auth.logout();
    this.onTapNavigation('/login');
    this.isAuthenticated();
  }

}
