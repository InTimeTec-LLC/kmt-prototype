import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleListFilterComponent } from './list-article.component';

describe('ContentComponent', () => {
  let component: ArticleListFilterComponent;
  let fixture: ComponentFixture<ArticleListFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticleListFilterComponent ]
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
