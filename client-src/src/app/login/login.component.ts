import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import {ToasterModule, ToasterService} from 'angular5-toaster';
import { MessageService } from '../../shared/service/message/message';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  model: any = {};
  loading = false;

  constructor(
      private router: Router, 
      private toasterService: ToasterService,
      private messageService: MessageService,
      private authenticationService: AuthenticationService) { 

      }

    ngOnInit() {
        // reset login status
        this.authenticationService.logout();
    }

    login() {
        this.loading = true;
        this.authenticationService.login(this.model.username, this.model.password)
            .subscribe(
                data => {
                    console.log(data);
                    this.messageService.sendMessage('set user type');
                    this.router.navigate(['/dashboard']);
                },
                error => {
                    this.loading = false;
                    this.toasterService.pop('error', 'Error', 'Username or password is incorrect');
                });
    }
}
