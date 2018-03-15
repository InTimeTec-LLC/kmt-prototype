import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { ToasterService } from 'angular5-toaster';
import { ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css'],
  encapsulation: ViewEncapsulation.None

})
export class ForgotPasswordComponent implements OnInit {
  forgotPasswordform: FormGroup;

  constructor(
    private authenticationService: AuthenticationService,
    private toasterService: ToasterService,
    private fb: FormBuilder
  ) {
   }

  ngOnInit() {
      this.forgotPasswordform = this.fb.group({
            email: new FormControl('', [ Validators.email]),
    });
  }

  onSubmit(value) {
    const email = value.controls['email'];
    this.authenticationService.forgotPassword(email.value)
            .subscribe(
                data => {
                  console.log(data);
                  this.toasterService.pop('success', '', data.success.message);
                },
                error => {
                    this.toasterService.pop('error', '', error.failure.message);
                });
    }


}
