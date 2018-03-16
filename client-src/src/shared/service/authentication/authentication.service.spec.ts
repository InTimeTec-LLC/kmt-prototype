import { getTestBed, TestBed } from '@angular/core/testing';
import { AuthenticationService } from './authentication.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { ResponseOptions } from '@angular/http';

let loginApiURL = environment.API_ENDPOINT + 'login';
let forgotPasswordURL = environment.API_ENDPOINT + 'forgotpassword?emailid=kmtadmin@mailinator.com';

describe('AuthenticationService', () => {
  const mockLoginResponse = {
    "user": {
        "id": "5aaa7d319d57d731bf6908cc",
        "userRole": "admin",
        "firstName": "ktm",
        "lastName": "admin",
        "email": "kmtadmin@mailinator.com",
        "lastLogin": null,
        "dateJoined": 1521122609606,
        "active": true,
        "password": null,
        "session": true
    },
    "success": {
        "status": true,
        "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MjExNDUwMTMsImVtYWlsIjoia210YWRtaW5AbWFpbGluYXRvci5jb20ifQ.w192Z146DcFpRTJdH_h8aFIBhyYdowxjsFV4I-H7JqA"
    }
};

  let injector: TestBed;
  let service: AuthenticationService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthenticationService]
    });

    injector = getTestBed();
    service = injector.get(AuthenticationService);
    httpMock = injector.get(HttpTestingController);
  });

  // Get login details
  it('Login', () => {
      service.login('kmtadmin@mailinator.com','Admin123').subscribe( (data) => {
        console.log(data);
        expect(data["success"]["status"]).toEqual(true);
      });

      const req = httpMock.expectOne(loginApiURL);
      expect(req.request.method).toBe('POST');
      req.flush(mockLoginResponse);
  });

  // Forgot password
  it('ForgotPassword', () => {
    service.forgotPassword('kmtadmin@mailinator.com').subscribe((res) => {
      console.log(res);
      expect(res).toBeDefined();
    });

    const req = httpMock.expectOne(forgotPasswordURL);
      expect(req.request.method).toBe('GET');
      req.flush("link to reset password");
  });

    // logout
  it('Logout user', () => {
    localStorage.setItem('currentUser', JSON.stringify(mockLoginResponse));
    service.logout();
    expect(service.token).toBeNull();
  });

  
  // Authentication successfull
  it('Authetication Successfull', () => {
   localStorage.setItem('currentUser', JSON.stringify(mockLoginResponse));
   expect(service.isAuthenticated()).toBeDefined();
  });

 // Authentication failed.
  it('Authentication failed', () => {
    localStorage.setItem('currentUser', "{}");
    expect(service.isAuthenticated()).toBeDefined(1);
  });

  // Get username successfull
  it('Get username success', () => {
    localStorage.setItem('currentUser', JSON.stringify(mockLoginResponse));
    expect(service.getUserName()).toEqual("ktm admin");
  });

  // Get username failure
  it('Get username failure', () => {
    localStorage.setItem('currentUser', null);
    expect(service.getUserName()).toBeNull();
  });

  // Get userType successfull
  it('Get userType success', () => {
    localStorage.setItem('currentUser', JSON.stringify(mockLoginResponse));
    expect(service.getUserType()).toEqual("admin");
  });

  // Get userType failure
  it('Get userType failure', () => {
    localStorage.setItem('currentUser', null);
    expect(service.getUserType()).toBeUndefined();
  });

  // Get access token successfull
  it('Get access token success', () => {
    localStorage.setItem('currentUser', JSON.stringify(mockLoginResponse));
    expect(service.getAccessToken()).toBeTruthy();
  });

  // Get access token failure
  it('Get access token failure', () => {
    localStorage.setItem('currentUser', null);
    expect(service.getAccessToken()).toBeNull();
  });


  // Get user id successfull
  it('Get user id success', () => {
    localStorage.setItem('currentUser', JSON.stringify(mockLoginResponse));
    expect(service.getUserId()).toEqual("5aaa7d319d57d731bf6908cc");
  });

  // Get user id failure
  it('Get user id failure', () => {
    localStorage.setItem('currentUser', null);
    expect(service.getUserId()).toBeNull();
  });

  // Get userrole successfull
  it('Get userrole success', () => {
    localStorage.setItem('currentUser', JSON.stringify(mockLoginResponse));
    expect(service.getUserRole()).toEqual("admin");
  });

  // Get userrole failure
  it('Get userrole failure', () => {
    localStorage.setItem('currentUser', null);
    expect(service.getUserRole()).toBeNull();
  });

});