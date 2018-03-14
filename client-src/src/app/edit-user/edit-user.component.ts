import { Component, OnInit, ViewChild  } from '@angular/core';
import { UserService } from '../../shared/service/user/user.service';
import { Router } from '@angular/router';
import { User } from '../../shared/modals/user';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import { ToasterService } from 'angular5-toaster';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss'],
})

export class EditUserComponent implements OnInit {

  users: User[];
  errorMessage: String;
  userName: String;
  user: FormGroup;
  userId: String;
  roles: any[];

  get cpwd() {
    return this.user.get('confirmPassword');
   }

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toasterService: ToasterService

  ) {

    this.activatedRoute.params.subscribe((params: any) => {
      this.userId = params['id'];
      if (this.userId) {

        this.userService.reteriveUserById(this.userId).subscribe((data: any) => {
          console.log(data);
          this.user.setValue({
            firstName: data.user.firstName,
            lastName: data.user.lastName,
            email: data.user.email,
            password: data.user.password,
            confirmPassword: data.user.password,
            userRole: data.user.userRole
          });
        });
      }
    });

    this.userService.listRoles().subscribe((data: any) => {
        this.roles = data.roles;
      });

  }

  ngOnInit() {
    this.user = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', Validators.required],
      password: ['', Validators.required],
      userRole: ['', Validators.required],
      email: ['', Validators.email],
      confirmPassword: ['', Validators.required],
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
    this.userService.updateUser(this.userId, value)
    .subscribe( data => {
                    this.toasterService.pop('success', '', data.success.message);
                    this.onCancle();
         },
                  error => this.toasterService.pop('error', '', error.error.success.message));
  }

  onCancle() {
    this.router.navigateByUrl('/userlist');
  }

  onDeactivate() {
    if (confirm('Are you sure to deactivate user ? ' + name)) {
      this.userService.deleteUser(this.userId)
    .subscribe( data => {
                    this.toasterService.pop('success', 'Success', data.success.message);
         },
                  error => this.toasterService.pop('error', 'Error', error.failure.message));
      }
    }
}
