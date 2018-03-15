import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';

@Component({
  selector: 'app-help',
  templateUrl: './help.component.html',
  styleUrls: ['./help.component.css']
})
export class HelpComponent implements OnInit {
  userType;
  constructor(private auth: AuthenticationService) {
    this.userType = this.auth.getUserType();
    console.log(this.userType);
  }

  ngOnInit() {
  }

}
