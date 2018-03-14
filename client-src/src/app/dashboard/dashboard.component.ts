import {Component, ViewChild, OnInit, Inject} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource, MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { User } from '../../shared/modals/user';
import { Router } from '@angular/router';
import { ToasterService } from 'angular5-toaster';
import { forEach } from '@angular/router/src/utils/collection';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { Aritcles } from '../../shared/modals/knowledge-base-article';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {

  knowledge_base_contents: any;
  displayedColumns = ['title', 'type', 'createdBy', 'createdDate'];
  displayedColumnEdited = ['title', 'type', 'status'];
  dataSource: MatTableDataSource<Aritcles>;
  dataSourceEdited: MatTableDataSource<Aritcles>;
  dataSourceAdded: MatTableDataSource<Aritcles>;
  searchTxt: any = '';
  finalTxt: any = '';
  articleList: any;
  filterList: any = [];
  articleEditedList: any = [];
  articleAddedList: any = [];
  pageNo = 0;
  articleBy = '';
  formattedResponse: any;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;


  constructor(
    private router: Router,
    private toasterService: ToasterService,
    private kbContentService: KnowledgeBaseArticleService,
    public dialog: MatDialog,
    private auth: AuthenticationService
    ) {
    }


  ngOnInit() {
    this.getArticleList(0, false, 'size=5&sort=createdTime,desc', 'pending_approvals');
    this.getArticleList(0, '', 'size=5&sort=lastModifiedTime,desc', 'recently_edited');
    this.getArticleList(0, '', 'size=5&sort=createdTime,desc', 'recently_added');
  }

  getArticleList(pageNum, status, sort, dashboardType) {
    let queryParam = '?page=' + pageNum + '&' + sort;
    if (status !== '' && status !== undefined && status !== null) {
        queryParam = queryParam.concat('&status=' + status);
    }
    this.kbContentService.listKnowledgeBaseArticle(queryParam)
    .subscribe(
        data => {
            const articleResponse = JSON.parse(JSON.stringify(data)).content;
            this.createData(articleResponse, dashboardType);
        },
        error => {
            this.toasterService.pop('error', '', error.error.message);
        });
}

createData(data, dashBoardType) {
  const aritcles: Aritcles[] = [];
  for (let i = 0; i < data.length; i++) {
    aritcles.push(this.createNewUser(data[i]));
  }
  switch ( dashBoardType ) {
    case 'pending_approvals':
        this.filterList = aritcles;
        this.dataSource = new MatTableDataSource(this.filterList);
    break;
    case 'recently_edited':
        this.articleEditedList = aritcles;
        this.dataSourceEdited = new MatTableDataSource(this.articleEditedList);
    break;
    case 'recently_added':
        this.articleAddedList = aritcles;
        this.dataSourceAdded = new MatTableDataSource(this.articleAddedList);
    break;
  }

}

onTapNavigation(route) {
  this.router.navigate([route]);
}

createNewUser(item: any): any {
  return {
      id: item.id,
      type: item.articleType.type,
      title: item.title,
      status: item.approved,
      content: item,
      size: 5,
      totalPages: 1,
      totalElements: 20,
      numberOfElements: 20,
      createdDate: item.createdTime,
      createdBy: item.createdBy.firstName + item.createdBy.lastName
  };
 }



}
