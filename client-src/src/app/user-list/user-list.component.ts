import {Component, ViewChild, OnInit, Inject} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource, MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { UserService } from '../../shared/service/user/user.service';
import { User } from '../../shared/modals/user';
import { Router } from '@angular/router';
import { ToasterService } from 'angular5-toaster';
import { forEach } from '@angular/router/src/utils/collection';

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

  userList: any;
  selectedFilter: any = {
    status: undefined,
    role: undefined
  };

  constructor(
    private userService: UserService,
    private router: Router,
    private toasterService: ToasterService,
    public dialog: MatDialog) {
        this.userService.listRoles().subscribe((data: any) => {
            this.userService.setRoles(data.roles);
        });
    }

    ngOnInit() {
        this.getUserList();
    }

    onTapActions(status, userId) {
        let type = 'deactivate';
        if (status) { type = 'activate'; }
        if (confirm('Would you like to ' + type + ' the user?')) {
            this.userService.activateDeactivateUsers(status, userId).subscribe(
                data => {
                    this.toasterService.pop('success', '', data.success.message);
                    this.getUserList();
                },
                error => {
                    this.toasterService.pop('error', '', error.success.message);
                });
            }
    }

    getUserList() {
        this.userService.listUser()
        .subscribe(
            data => {
                this.userList = JSON.parse(JSON.stringify(data.users));
                console.log(data.users);
                this.createData(data.users);
            },
            error => {
                this.toasterService.pop('error', '', error.success.message);
            });
    }

    onTapNavigation(route) {
        this.router.navigate([route]);
    }

    onTapFilterIcon() {
        const dialogRef = this.dialog.open(UserListFilterComponent, {
            width: '274px',
            data: {selFilter : this.selectedFilter}
        });

        dialogRef.afterClosed().subscribe(result => {
            let filteStatus = [];
            let filteRole = [];
            if (result && result.status !== undefined) {
                this.selectedFilter.status = result.status;
                if (result.status === 'Activate') {
                    this.userList.forEach(function(element) {
                        if (element.active) { filteStatus.push(element); }
                    }.bind(this));
                } else if (result.status === 'Deactivate') {
                    this.userList.forEach(function(element) {
                        if (!element.active) { filteStatus.push(element); }
                    }.bind(this));
                }
            } else {
                this.selectedFilter.status = undefined;
                filteStatus = this.userList;
            }

            if (result && result.role !== undefined) {
                this.selectedFilter.role = result.role;
                filteStatus.forEach(function(element) {
                    if (element.userRole === String(result.role).toLowerCase()) { filteRole.push(element); }
                }.bind(this));
            } else {
                filteRole = filteStatus;
                this.selectedFilter.role = undefined;
            }

            this.createData(filteRole);
        });
    }

    createData(data) {
        const users: User[] = [];
        for (let i = 0; i < data.length; i++) {
            users.push(this.createNewUser(data[i]));
        }

        console.log(users);
        this.dataSource = new MatTableDataSource(users);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    }

  createNewUser(item: any): User {
    return {
        id: item.id,
        firstName: item.firstName,
        lastName: item.lastName,
        email: item.email,
        password: item.password,
        userRole : item.userRole,
        status : item.active,
        createdOn : item.dateJoined
    };
   }
}

@Component({
    selector: 'app-user-filter',
    templateUrl: 'user-filter.html',
  })
  export class UserListFilterComponent {
    statusList = ['Activate', 'Deactivate'];
    roleList: any[];
    selectedStatus: any;
    selectedRole: any;

    constructor(
        private userService: UserService,
        private dialogRef: MatDialogRef<UserListFilterComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {
            this.roleList = this.userService.getRoles();
            if (data.selFilter !== undefined) {
                if (data.selFilter.status !== undefined) {
                    this.selectedStatus = data.selFilter.status;
                }

                if (data.selFilter.role !== undefined) {
                    this.selectedRole = data.selFilter.role;
                }
            }
        }

    onCancelClick() {
        this.dialogRef.close();
    }
  }
