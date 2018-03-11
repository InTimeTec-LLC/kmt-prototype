import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { ToasterService } from 'angular5-toaster';
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
      private authenticationService: AuthenticationService) {}

    ngOnInit() {
    }

    login() {
        this.loading = true;
        this.authenticationService.login(this.model.username, this.model.password)
            .subscribe(
                data => {
                    this.messageService.sendMessage('set user type');
                    this.messageService.sendMessage('openMatDrawer');
                    this.router.navigate(['/dashboard']);
                },
                error => {
                    this.loading = false;
                    this.toasterService.pop('error', '', 'Username or password is incorrect');
                });
    }
}
