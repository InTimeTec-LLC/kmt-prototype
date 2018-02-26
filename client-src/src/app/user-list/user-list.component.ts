import { Component, OnInit } from '@angular/core';
import { UserService } from '../../shared/service/user/user.service';


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  listOfUsers : any;  
  constructor(private userService: UserService) { }

  ngOnInit() {
     this.getUserList();
  }

  getUserList() {
      this.userService.listUser()
      .subscribe(
          data => {
              this.listOfUsers = data.users;
              console.log(this.listOfUsers);
          },
          error => {
              // Need to perform
          });
  }
}
