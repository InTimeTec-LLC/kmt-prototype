import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovedKnowledgeBaseArticleComponent } from './approved-knowledge-base-article.component';

describe('ViewKnowledgeBaseArticleComponent', () => {
  let component: ApprovedKnowledgeBaseArticleComponent;
  let fixture: ComponentFixture<ApprovedKnowledgeBaseArticleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApprovedKnowledgeBaseArticleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApprovedKnowledgeBaseArticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
