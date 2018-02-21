import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuillEditorSampleComponent } from './quill-editor-sample.component';

describe('QuillEditorSampleComponent', () => {
  let component: QuillEditorSampleComponent;
  let fixture: ComponentFixture<QuillEditorSampleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuillEditorSampleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuillEditorSampleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
