import {Component, ViewChild, OnInit, Inject, OnDestroy} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource, MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { User } from '../../shared/modals/user';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { ToasterService } from 'angular5-toaster';
import { forEach } from '@angular/router/src/utils/collection';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Aritcles } from '../../shared/modals/knowledge-base-article';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';

/**
 * @title Data table with sorting, pagination, and filtering.
 */
@Component({
  selector: 'app-content',
  templateUrl: './list-article.component.html',
  styleUrls: ['./list-article.component.scss']
})

export class ListArticleComponent implements OnInit, OnDestroy {
  displayedColumns = ['title', 'type', 'status', 'actions'];
  dataSource: MatTableDataSource<Aritcles>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  searchTxt: any = '';
  finalTxt: any = '';
  articleList: any;
  filterList: any = [];
  appliedFilter: any = [];
  selectedFilter: any = {
    status: undefined,
    type: undefined,
    typeId : undefined
  };
  currentUserId: any;
  currentUserRole: any;
  totalNumberItems: Number;
  clickedOnApprovals: Boolean = false;

  bFilterStatus = undefined;
  pageNo = 0;
  articleBy = '';
  navigationSubscription;
  compType = '';

  constructor(
    private router: Router,
    private aRoute: ActivatedRoute,
    private toasterService: ToasterService,
    private kbContentService: KnowledgeBaseArticleService,
    public dialog: MatDialog,
    private auth: AuthenticationService
    ) {
        this.kbContentService.listKnowledgeBaseArticleTypes().subscribe((data: any) => {
            this.kbContentService.setTypes(data.types);
            this.currentUserId = this.auth.getUserId();
            this.currentUserRole = this.auth.getUserRole();
        });

        this.navigationSubscription = this.router.events.subscribe((e: any) => {
            if (e instanceof NavigationEnd) {
              if (this.router.url === '/articles') {
                this.compType = 'list';
                this.initData();
              }
            }
        });
    }

    ngOnDestroy() {
        if (this.navigationSubscription) {
           this.navigationSubscription.unsubscribe();
        }
    }

    initData() {
        this.getArticleList(0, this.articleBy , this.selectedFilter.typeId, this.bFilterStatus, this.finalTxt);
    }

    ngOnInit() {
    }

    onClickviewAll() {
        this.clickedOnApprovals = false;
        this.articleBy = '';
        this.selectedFilter = {
            status: undefined,
            type: undefined,
            typeId : undefined
        };
        this.bFilterStatus = undefined;
        this.finalTxt = '';
        this.ngOnInit();
    }

    onClickAssignedCreatedList(fromWhich) {
        this.clickedOnApprovals = true;
        this.articleBy = fromWhich;
        this.getArticleList(0, this.articleBy , this.selectedFilter.typeId, this.bFilterStatus, this.finalTxt);
    }

    onTapSearchIcon() {
        console.log(this.searchTxt);
        this.finalTxt = this.searchTxt.trim();
        this.finalTxt = this.finalTxt.toLowerCase();
        console.log(this.finalTxt);
        this.getArticleList(0, this.articleBy , this.selectedFilter.typeId, this.bFilterStatus, this.finalTxt);
    }

    onPaginateChange(pageInfo) {
        this.pageNo = pageInfo.pageIndex;
        this.getArticleList(this.pageNo, this.articleBy , this.selectedFilter.typeId, this.bFilterStatus, this.finalTxt);
    }

    onTapDelete(articleId) {
        if (confirm('Would you like to delete the article?')) {
            this.kbContentService.deleteArticle(articleId).subscribe(
                data => {
                    this.toasterService.pop('success', '', data.success.message);
                    this.getArticleList(this.pageNo, this.articleBy , this.selectedFilter.typeId, this.bFilterStatus, this.finalTxt);
                },
                error => {
                    this.toasterService.pop('error', '', error.success.message);
                });
            }
    }

    getArticleList(pageNum, filter, type, status, search) {
        console.log(pageNum, filter, type, status, search);
        let queryParam = '?page=' + pageNum;
        if (filter !== '' && filter !== undefined && filter !== null) {
            queryParam = queryParam.concat('&filter=' + filter);
        }
        if (type !== '' && type !== undefined && type !== null) {
            queryParam = queryParam.concat('&type=' + type);
        }
        if (status !== '' && status !== undefined && status !== null) {
            queryParam = queryParam.concat('&status=' + status);
        }
        if (search !== '' && search !== undefined && search !== null) {
            queryParam = queryParam.concat('&search=' + search.toLowerCase());
        }

        console.log(queryParam);
        this.kbContentService.listKnowledgeBaseArticle(queryParam)
        .subscribe(
            data => {
                this.articleList = JSON.parse(JSON.stringify(data)).content;
                console.log(this.articleList);
                this.totalNumberItems = JSON.parse(JSON.stringify(data)).totalElements;
                this.createData(this.articleList);
            },
            error => {
                this.toasterService.pop('error', '', error.error.message);
            });
    }

    onTapNavigation(route, param) {
        this.compType = route;
        if (param) {
            this.router.navigate([route, param], {relativeTo: this.aRoute} );
        } else {
            this.router.navigate([route], {relativeTo: this.aRoute} );
        }
    }

    onTapFilterIcon() {
        const dialogRef = this.dialog.open(ArticleListFilterComponent, {
            width: '274px',
            data: {selFilter : this.selectedFilter}
        });

        dialogRef.afterClosed().subscribe(result => {
            console.log(result);
            if (result) {
                this.appliedFilter = [];
                this.bFilterStatus = undefined;
                if (result.status !== undefined) {
                    this.selectedFilter.status = result.status;
                    if (result.status === 'Published') {
                        this.bFilterStatus = true;
                    } else if (result.status === 'Unpublished') {
                        this.bFilterStatus = false;
                    }
                    this.appliedFilter.push(result.status);
                } else {
                    if (result === 'doClear') {
                        this.selectedFilter.status = undefined;
                    }
                }

                if (result.type !== undefined) {
                    this.appliedFilter.push(result.type);
                    this.selectedFilter.type = result.type;
                    this.selectedFilter.typeId = (this.kbContentService.getTypes().find(i => (i.type === result.type))).id;
                } else {
                    if (result === 'doClear') {
                        this.selectedFilter.type = undefined;
                        this.selectedFilter.typeId = undefined;
                    }
                }
                console.log(this.selectedFilter);
                this.getArticleList(0, this.articleBy , this.selectedFilter.typeId, this.bFilterStatus, this.finalTxt);
            }
        });
    }

    createData(data) {
        const aritcles: Aritcles[] = [];
        for (let i = 0; i < data.length; i++) {
          aritcles.push(this.createNewUser(data[i]));
        }

        console.log(aritcles);
        this.filterList = aritcles;
        this.dataSource = new MatTableDataSource(aritcles);
    }

  createNewUser(item: any): any {
    return {
        id: item.id,
        type: item.articleType.type,
        title: item.title,
        status: item.approved,
        content: item,
        size: 20,
        totalPages: 1,
        totalElements: 20,
        numberOfElements: 20
    };
   }
}

@Component({
    selector: 'app-article-filter',
    templateUrl: 'article-filter.html',
  })
  export class ArticleListFilterComponent {
    statusList = ['Published', 'Unpublished'];
    typesList: any[];
    selectedStatus: any;
    selectedType: any;

    constructor(
      private kbContentService: KnowledgeBaseArticleService,
        private dialogRef: MatDialogRef<ArticleListFilterComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {
            this.typesList = this.kbContentService.getTypes();
            this.typesList.sort(function(a, b) {
                if (a.type < b.type) {return -1;
                }
                if (a.type > b.type) { return 1;
                }
                return 0;
            });
            if (data.selFilter !== undefined) {
                if (data.selFilter.status !== undefined) {
                    this.selectedStatus = data.selFilter.status;
                }

                if (data.selFilter.type !== undefined) {
                    this.selectedType = data.selFilter.type;
                }
            }
        }

    onCancelClick() {
        this.dialogRef.close('doClear');
    }
  }
