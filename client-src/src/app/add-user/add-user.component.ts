import { Component, OnInit, ViewChild  } from '@angular/core';
import { UserService } from '../../shared/service/user/user.service';
import { Router } from '@angular/router';
import { User } from '../../shared/modals/user';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})

export class AddUserComponent implements OnInit {

  articles: User[];
  errorMessage: String;
  addUserName: String;
  user: FormGroup;


  get cpwd() {
    return this.user.get('confirmPassword');
   }

  constructor(
    private userService: UserService,
    private fb: FormBuilder
  ) {
  }

  ngOnInit() {
    this.user = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(10)]],
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

  onSubmit({value, valid}: {value: User, valid: boolean }) {

    this.userService.createUser(value)
    .subscribe( user => {
                    this.addUserName = user.firstName;
         },
                  error => this.errorMessage = <any>error);

  }
}
