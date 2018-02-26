import { Component, OnInit, ViewChild  } from '@angular/core';
import { UserService } from '../../shared/service/user/user.service';
import { Router } from '@angular/router';
import { User } from '../../shared/modals/user';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})

export class EditUserComponent implements OnInit {

  users: User[];
  errorMessage: String;
  userName: String;
  user: FormGroup;
  userId: Number;


  get cpwd() {
    return this.user.get('confirmPassword');
   }

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute

  ) {

    this.activatedRoute.params.subscribe((params: any) => {
      this.userId = params['id'];
      if (this.userId) {
        this.userService.reteriveUserById(this.userId).subscribe((user: User) => {
          this.user.setValue({
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email,
            password: user.password,
            confirmPassword: user.password,
            userRole: user.userRole
          });
        });
      }
    });
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

    this.userService.updateUser(this.userId, value)
    .subscribe( user => {
                    this.userName = user.firstName;
         },
                  error => this.errorMessage = <any>error);
  }

  onCancle() {
    this.router.navigateByUrl('/userlist');
  }

}
