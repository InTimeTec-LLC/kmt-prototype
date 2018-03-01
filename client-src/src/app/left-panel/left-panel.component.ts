import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
 
import { MessageService } from '../../shared/service/message/message';


@Component({
  selector: 'app-left-panel',
  templateUrl: './left-panel.component.html',
  styleUrls: ['./left-panel.component.scss']
})
export class LeftPanelComponent implements OnInit {

  userType : any;
  subscription: Subscription;

  constructor(private auth: AuthenticationService, private router: Router,private messageService: MessageService) { 
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
    
  }

  refreshUserType() {
    this.userType = this.auth.getUserType();
  }

  onTapNavigation(route) {
    this.router.navigate([route]);
  }

  isAuthenticated() {
    return this.auth.isAuthenticated();
  }

  getUserName() {
    return this.auth.getUserName();
  }

  logout() {
    this.auth.logout();
    this.router.navigateByUrl('login');
  }

}
