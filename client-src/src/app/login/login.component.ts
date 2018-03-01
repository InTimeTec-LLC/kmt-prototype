import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import {ToasterModule, ToasterService, ToasterConfig} from 'angular5-toaster';
import { MessageService } from '../../shared/service/message/message';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  model: any = {};
  loading = false;

  private toasterconfig : ToasterConfig = 
        new ToasterConfig({
            showCloseButton: false, 
            tapToDismiss: false, 
            timeout: 2000,
            positionClass : 'toast-top-center',
            animate : 'fade'
        });

  constructor(
      private router: Router, 
      private toasterService: ToasterService,
      private messageService: MessageService,
      private authenticationService: AuthenticationService) {}

    ngOnInit() {
       
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
                    this.toasterService.pop('error', '', 'Username or password is incorrect');
                });
    }
}
