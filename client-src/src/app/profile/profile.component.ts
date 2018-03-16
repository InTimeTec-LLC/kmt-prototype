import { Component, OnInit, ViewChild  } from '@angular/core';
import { UserService } from '../../shared/service/user/user.service';
import { Router } from '@angular/router';
import { User } from '../../shared/modals/user';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import { ToasterService } from 'angular5-toaster';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {

  users: User[];
  errorMessage: String;
  userName: String;
  user: FormGroup;
  userId: String;
  roles: any[];
  userRole: any;
  readOnly: boolean;
  bUserRole;

  get cpwd() {
    return this.user.get('confirmPassword');
   }

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toasterService: ToasterService,
    private authService: AuthenticationService

  ) {

    this.userRole = this.authService.getUserRole();
    this.readOnly = true;
    this.userId = this.authService.getUserId();
    if (this.userId) {
      this.userService.reteriveUserById(this.userId).subscribe((data: any) => {
        this.user.setValue({
          firstName: data.user.firstName,
          lastName: data.user.lastName,
          email: data.user.email,
          password: data.user.password,
          confirmPassword: data.user.password,
          userRole: data.user.userRole
        });
        this.bUserRole = data.user.userRole;
      });
    }

    this.userService.listRoles().subscribe((data: any) => {
        this.roles = data.roles;
    });
  }

  ngOnInit() {
    this.user = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', Validators.required],
      password: [''],
      userRole: [{value: '', disabled: this.readOnly}, Validators.required],
      email: ['', Validators.email],
      confirmPassword: [''],
    }, {validator: this.validateConfirmPassword('password', 'confirmPassword')});

  }

  validateConfirmPassword(passwordKey: string, passwordConfirmationKey: string) {
    return (group: FormGroup) => {
      const passwordInput = group.controls[passwordKey],
          passwordConfirmationInput = group.controls[passwordConfirmationKey];
      if (passwordInput.value !== passwordConfirmationInput.value) {
        return passwordConfirmationInput.setErrors({notEquivalent: true});
      }
        return passwordConfirmationInput.setErrors(null);
    };
  }

  onSubmit({value, valid}: {value: any, valid: boolean }) {
    delete value.confirmPassword;
    if (value.password === '' || value.password === null) {
      delete value.password;
    }

    // Temp fix. It is added due userRole is not coming in value object while role field is diabled.
    if (this.readOnly) {
      value.userRole = this.bUserRole;
    }

    this.userService.updateUser(this.userId, value)
    .subscribe( data => {
                    this.toasterService.pop('success', '', data.success.message);
                    this.onCancle();
         },
                  error => this.toasterService.pop('error', '', error.error.success.message));
  }

  onCancle() {
    this.router.navigateByUrl('');
  }
}
