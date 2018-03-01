import {Component, ViewChild, OnInit} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import { UserService } from '../../shared/service/user/user.service';
import { User } from '../../shared/modals/user';
import { Router } from '@angular/router';

/**
 * @title Data table with sorting, pagination, and filtering.
 */
@Component({
    selector: 'app-user-list',
    templateUrl: './user-list.component.html',
    styleUrls: ['./user-list.component.scss']
})

export class UserListComponent implements OnInit {
  displayedColumns = ['name', 'email', 'role', 'status', 'createdon', 'actions'];
  dataSource: MatTableDataSource<User>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private userService: UserService, private router: Router) {
    
  }

    ngOnInit() {
        this.getUserList();
    }

    getUserList() {
        this.userService.listUser()
        .subscribe(
            data => {
                console.log(data.users);
                this.createData(data.users);
            },
            error => {
                // Need to perform
            });
    }

    onTapNavigation(route) {
        this.router.navigate([route]);
    }

    createData(data) {
        const users: User[] = [];
        for (let i = 0; i < data.length; i++) 
        { 
            users.push(this.createNewUser(data[i])); 
        }

        this.dataSource = new MatTableDataSource(users);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  createNewUser(item:any): User {
    return {
        id: item.id,
        firstName: item.firstName,
        lastName: item.lastName,
        email: item.email,
        password: item.password,
        userRole : item.userRole,
        status : '',
        createdOn : ''
    };
   }
}