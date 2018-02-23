import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomQuillEditorComponent } from './quill-editor.component';

describe('QuillEditorComponent', () => {
  let component: CustomQuillEditorComponent;
  let fixture: ComponentFixture<CustomQuillEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomQuillEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomQuillEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
