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

  searchTxt: any = '';
  finalTxt: any = '';
  userList: any;
  totalNumberItems: Number;
  filterList: any = [];
  selectedFilter: any = {
    status: undefined,
    role: undefined
  };

  bFilterStatus = undefined;
  pageNo = 0;

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
        this.getUserList(0, '' , this.selectedFilter.role, this.bFilterStatus, this.finalTxt);
    }

    onTapSearchIcon() {
        console.log(this.searchTxt);
        this.finalTxt = this.searchTxt.trim();
        this.finalTxt = this.finalTxt.toLowerCase();
        console.log(this.finalTxt);
        this.getUserList(0, '' , this.selectedFilter.role, this.bFilterStatus, this.finalTxt);
    }

    onPaginateChange(pageInfo) {
        this.pageNo = pageInfo.pageIndex;
        this.getUserList(this.pageNo, '' , this.selectedFilter.role, this.bFilterStatus, this.finalTxt);
    }

    onTapActions(status, userId) {
        let type = 'deactivate';
        if (status) { type = 'activate'; }
        if (confirm('Would you like to ' + type + ' the user?')) {
            this.userService.activateDeactivateUsers(status, userId).subscribe(
                data => {
                    this.toasterService.pop('success', '', data.success.message);
                    this.getUserList(this.pageNo, '' , this.selectedFilter.role, this.bFilterStatus, this.finalTxt);
                },
                error => {
                    this.toasterService.pop('error', '', error.success.message);
                });
            }
    }

    getUserList(pageNum, sortField, role, status, search) {
        console.log(pageNum, sortField, role, status, search);
        let queryParam = '?page=' + pageNum;
        if (role !== '' && role !== undefined && role !== null) {
            queryParam = queryParam.concat('&role=' + role.toLowerCase());
        }
        if (status !== '' && status !== undefined && status !== null) {
            queryParam = queryParam.concat('&status=' + status);
        }
        if (search !== '' && search !== undefined && search !== null) {
            console.log(search);
            queryParam = queryParam.concat('&search=' + search.toLowerCase());
        }
        if (sortField !== '' && sortField !== undefined && sortField !== null) {
            queryParam = queryParam.concat('&sortField=' + sortField);
        }
        console.log(queryParam);

        this.userService.listUser(queryParam)
        .subscribe(
            data => {
                console.log(data);
                this.totalNumberItems = data.totalElements;
                this.createData(data.content);
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
            console.log(result);
            if (result) {
                let filterRole = '';
                this.bFilterStatus = undefined;
                if (result.status !== undefined) {
                    this.selectedFilter.status = result.status;
                    if (result.status === 'Active') {
                        this.bFilterStatus = true;
                    } else if (result.status === 'Inactive') {
                        this.bFilterStatus = false;
                    }
                } else {
                    if (result === 'doClear') { this.selectedFilter.status = undefined; }
                }

                if (result.role !== undefined) {
                    this.selectedFilter.role = result.role;
                    filterRole = String(result.role).toLowerCase();
                } else {
                    if (result === 'doClear') { this.selectedFilter.role = undefined; }
                }
                this.getUserList(0, '' , filterRole, this.bFilterStatus, this.finalTxt);
            }
        });
    }

    createData(data) {
        const users: User[] = [];
        for (let i = 0; i < data.length; i++) {
            users.push(this.createNewUser(data[i]));
        }

        console.log(users);
        this.filterList = users;
        this.dataSource = new MatTableDataSource(users);
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
    statusList = ['Active', 'Inactive'];
    roleList: any[];
    selectedStatus: any;
    selectedRole: any;

    constructor(
        private userService: UserService,
        private dialogRef: MatDialogRef<UserListFilterComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {
            this.roleList = this.userService.getRoles();
            this.roleList.sort(function(a, b) {
                if (a.role < b.role) {return -1;
                }
                if (a.role > b.role) { return 1;
                }
                return 0;
            });
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
        this.dialogRef.close('doClear');
    }
  }
