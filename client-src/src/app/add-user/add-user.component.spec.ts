import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import {MaterialModule} from '../../shared/material.module';
import { UserService } from '../../shared/service/user/user.service';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { RouterTestingModule } from '@angular/router/testing';
import { ToasterService } from 'angular5-toaster';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddUserComponent } from './add-user.component';

describe('AddUserComponent', () => {
  let component: AddUserComponent;
  let fixture: ComponentFixture<AddUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers:[UserService, AuthenticationService, ToasterService],
      imports:[ReactiveFormsModule, MaterialModule, HttpClientModule, RouterTestingModule, BrowserAnimationsModule],
      declarations: [ AddUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
