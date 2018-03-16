import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KnowledgeBaseSearchComponent } from './knowledge-base-search.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
describe('KnowledgeBaseSearchComponent', () => {
  let component: KnowledgeBaseSearchComponent;
  let fixture: ComponentFixture<KnowledgeBaseSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KnowledgeBaseSearchComponent ],
      imports: [ FormsModule, ReactiveFormsModule, RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KnowledgeBaseSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
