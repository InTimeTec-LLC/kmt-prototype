import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleListFilterComponent } from './list-article.component';
import { MaterialModule } from '../../shared/material.module';
import { QuillModule } from 'ngx-quill';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { HttpClientModule } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
describe('ContentComponent', () => {
  let component: ArticleListFilterComponent;
  let fixture: ComponentFixture<ArticleListFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticleListFilterComponent ],
      imports: [ MaterialModule, HttpClientModule],
      providers: [ KnowledgeBaseArticleService,
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: [] }]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticleListFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
