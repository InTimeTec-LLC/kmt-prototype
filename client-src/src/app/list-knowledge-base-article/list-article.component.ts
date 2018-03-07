import {Component, ViewChild, OnInit, Inject} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource, MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { User } from '../../shared/modals/user';
import { Router } from '@angular/router';
import { ToasterService } from 'angular5-toaster';
import { forEach } from '@angular/router/src/utils/collection';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Aritcles } from '../../shared/modals/knowledge-base-article';

/**
 * @title Data table with sorting, pagination, and filtering.
 */
@Component({
  selector: 'app-content',
  templateUrl: './list-article.component.html',
  styleUrls: ['./list-article.component.scss']
})

export class ListArticleComponent implements OnInit {
  displayedColumns = ['title', 'type', 'status', 'actions'];
  dataSource: MatTableDataSource<Aritcles>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  articleList: any;

  constructor(
    private router: Router,
    private toasterService: ToasterService,
    private kbContentService: KnowledgeBaseArticleService,
    public dialog: MatDialog) {
        this.kbContentService.listKnowledgeBaseArticleTypes().subscribe((data: any) => {
          this.kbContentService.setTypes(data.types);
      });
    }

    ngOnInit() {
        this.getArticleList();
    }

    onTapActions() {
    }

    getArticleList() {
        this.kbContentService.listKnowledgeBaseArticle()
        .subscribe(
            data => {
                this.articleList = JSON.parse(JSON.stringify(data)).content;
                console.log(this.articleList);
                this.createData(this.articleList);
            },
            error => {
                this.toasterService.pop('error', '', error.error.message);
            });
    }

    onTapNavigation(route) {
        this.router.navigate([route]);
    }

    onTapFilterIcon() {
        const dialogRef = this.dialog.open(ArticleListFilterComponent, {
            width: '274px',
            data: {}
        });

        dialogRef.afterClosed().subscribe(result => {
            let filterStatus = [];
            let filteType = [];
            if(result && result.status !== undefined) {
                if(result.status === 'Published') {
                    this.articleList.forEach(function(element) {
                        if (element.active) {
                            filterStatus.push(element);
                        }
                    }.bind(this));
                } else if (result.status === 'Unpublished') {
                    this.articleList.forEach(function(element) {
                        if (!element.active) { filterStatus.push(element); }
                    }.bind(this));
                }
            } else {
              filterStatus = this.articleList;
            }

            if (result && result.type !== undefined) {
              filterStatus.forEach(function(element){
                    if (element.type === String(result.type).toLowerCase()) { filteType.push(element); }
                }.bind(this));
            } else {
              filteType = filterStatus;
            }

            this.createData(filteType);
        });
    }

    createData(data) {
        const aritcles: Aritcles[] = [];
        for (let i = 0; i < data.length; i++) {
          aritcles.push(this.createNewUser(data[i]));
        }

        console.log(aritcles);
        this.dataSource = new MatTableDataSource(aritcles);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    }

  createNewUser(item: any): any {
    return {
        id: item.id,
        type: item.articleType.type,
        title: item.title,
        status: item.approved,
        content: '',
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
            console.log(this.typesList);
        }

    onCancelClick() {
        this.dialogRef.close();
    }
  }