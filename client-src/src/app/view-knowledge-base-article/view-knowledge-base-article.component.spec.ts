import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewKnowledgeBaseArticleComponent } from './view-knowledge-base-article.component';

describe('ViewKnowledgeBaseArticleComponent', () => {
  let component: ViewKnowledgeBaseArticleComponent;
  let fixture: ComponentFixture<ViewKnowledgeBaseArticleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewKnowledgeBaseArticleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewKnowledgeBaseArticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
