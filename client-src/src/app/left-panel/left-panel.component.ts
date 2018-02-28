import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-left-panel',
  templateUrl: './left-panel.component.html',
  styleUrls: ['./left-panel.component.scss']
})
export class LeftPanelComponent implements OnInit {

  userType : any;
  constructor(private auth: AuthenticationService, private router: Router) { 
     this.userType = this.auth.getUserType(); 
  }

  ngOnInit() {
    
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
