import { Component, OnInit } from '@angular/core';
import {MatPaginator} from '@angular/material';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { ToasterService } from 'angular5-toaster';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-search-knowledge-base',
  templateUrl: './search-knowledge-base.component.html',
  styleUrls: ['./search-knowledge-base.component.css']
})
export class SearchKnowledgeBaseComponent implements OnInit {

  searchTxt: string;
  kbSearchResults: any = [];
  sortField: string;
  totalNumberItems: Number;

  constructor(
    private kbContentService: KnowledgeBaseArticleService,
    private toasterService: ToasterService,
    private activateRoute: ActivatedRoute
  ) {
      this.sortField = 'title';
      this.kbSearchResults = {'content': [], 'totalElements': 0 };
   }

  ngOnInit() {
    this.getKnowledgeBaseResult(0, this.sortField, 'title');
    this.activateRoute
      .queryParams
      .subscribe(params => {
         this.searchTxt = params['search'] || '';
         this.getKnowledgeBaseResult(0, this.sortField, this.searchTxt);
      });
  }

  onPaginateChange(pageInfo) {
    this.getKnowledgeBaseResult(pageInfo.pageIndex, this.sortField, this.searchTxt);
  }

  onSubmit() {
    this.getKnowledgeBaseResult(0, this.sortField, this.searchTxt);
  }

  getKnowledgeBaseResult(pageNum, sortField, search) {
      const queryParam = this.getQueryParam(pageNum, sortField, search);
      this.kbContentService.listKnowledgeBaseSearchResults(queryParam)
            .subscribe(
                data => {
                    this.kbSearchResults = data;
                },
                error => {
                    this.toasterService.pop('error', '', error.success.message);
                });
        }


  getQueryParam (pageNum, sortField, search) {
    let queryParam = '?size=10&page=' + pageNum;
    if (search !== '' && search !== undefined && search !== null) {
      console.log(search);
      queryParam = queryParam.concat('&search=' + search.toLowerCase());
    }
    if (sortField !== '' && sortField !== undefined && sortField !== null) {
      // queryParam = queryParam.concat('&sort=' + sortField);
    }
    return queryParam;
  }

 }



