import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchKnowledgeBaseComponent } from './search-knowledge-base.component';

describe('SearchKnowledgeBaseComponent', () => {
  let component: SearchKnowledgeBaseComponent;
  let fixture: ComponentFixture<SearchKnowledgeBaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchKnowledgeBaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchKnowledgeBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
