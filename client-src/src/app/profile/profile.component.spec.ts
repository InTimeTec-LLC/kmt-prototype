import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileComponent } from './profile.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../../shared/material.module';
import { UserService } from '../../shared/service/user/user.service';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationService } from '../../shared/service/authentication/authentication.service';
import { RouterTestingModule } from '@angular/router/testing';
import { ToasterService } from 'angular5-toaster';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfileComponent ],
      imports: [ FormsModule, ReactiveFormsModule, MaterialModule, HttpClientModule, RouterTestingModule, BrowserAnimationsModule],
      providers: [ UserService, AuthenticationService, ToasterService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
