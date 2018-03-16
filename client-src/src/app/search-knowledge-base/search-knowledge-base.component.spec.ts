import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchKnowledgeBaseComponent } from './search-knowledge-base.component';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { MaterialModule } from '../../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { KnowledgeBaseArticleService } from '../../shared/service/knowledge-base-article/knowledge-base-article.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { ToasterService} from 'angular5-toaster';
import { RouterTestingModule } from '@angular/router/testing';
describe('SearchKnowledgeBaseComponent', () => {
  let component: SearchKnowledgeBaseComponent;
  let fixture: ComponentFixture<SearchKnowledgeBaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchKnowledgeBaseComponent ],
      imports: [ Ng4LoadingSpinnerModule, MaterialModule, FormsModule, ReactiveFormsModule, HttpClientModule, HttpModule, RouterTestingModule],
      providers: [ KnowledgeBaseArticleService, ToasterService]
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
