import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DashboardComponent implements OnInit {

  knowledge_base_contents: any;

  constructor(private kbContentService: KnowledgeBaseArticleService) {
  }

  ngOnInit() {
    /* his.kbContentService.listKnowledgeBaseArticle().subscribe(
      result => {
        this.knowledge_base_contents = result;
        },
      error => {
    }
  );*/
  }
}
