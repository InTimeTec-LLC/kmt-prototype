import { TestBed, async, inject} from '@angular/core/testing';
import { HttpModule, Http , Response,  ResponseOptions,  XHRBackend} from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { MockBackend } from '@angular/http/testing';
import { Mock } from 'protractor/built/driverProviders';
import { AuthenticationService } from './authentication.service';

describe('AuthenticationService', () => {
  var loginResponse = `{"success": { 
    "status": true, 
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MTkzMTQwNjcsInVzZXJuYW1lIjoiaGFyaXNoIn0.i6JjnKXl8Wcy3mP1NEXgdNhE7hJ5LHaqPcgn5_Yops4"
} ,
"user":  {
  "id":"1",
   "firstName": "John",
   "lastName": "Doe",
   "email": "john.doe@email.com",
   "password": "123",
   "userRole": "manager"
 }
}`;

let service: AuthenticationService;
let backend: MockBackend;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule, HttpClientModule],
      providers: [
        AuthenticationService, 
        MockBackend, 
         
        {
          provide: XHRBackend,
          useClass: MockBackend,
          
        }]
    });
    // backend = TestBed.get(MockBackend);
    // service = TestBed.get(AuthenticationService);
      });

      it('login should return login response', inject([AuthenticationService, XHRBackend], (authenticationService, mockBackend) => {
          mockBackend.connections.subscribe((connection) => {
                                                              connection.mockRespond(new Response(new ResponseOptions(
                                                                {
                                                                body: JSON.stringify(loginResponse)
                                                              }))
                                                            );

    
                                });

          authenticationService.login('john.doe@email.com','123').subscribe((data) => {
              expect(data).toBeUndefined();
          });

      }));

    //service created
    it('should be created', inject([AuthenticationService], (service: AuthenticationService) => {
      expect(service).toBeTruthy();
    }));

    // logout
    it('Logout user', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', loginResponse);
      service.logout();
      expect(service.token).toBeNull();
    }));

    // Authentication successfull
    it('Authetication Successfull', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', loginResponse);
      expect(service.isAuthenticated()).toBeTruthy();
    }));

    // Authentication failed.
    it('Authentication failed', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', "{}");
      expect(service.isAuthenticated()).toBeFalsy();
    }));

    // Get username successfull
    it('Get username success', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', loginResponse);
      expect(service.getUserName()).toEqual("John Doe");
    }));

    // Get username failure
    it('Get username failure', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', null);
      expect(service.getUserName()).toBeNull();
    }));

    // Get userrole successfull
    it('Get userrole success', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', loginResponse);
      expect(service.getUserType()).toEqual("manager");
    }));

    // Get userrole failure
    it('Get userrole failure', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', null);
      expect(service.getUserType()).toBeUndefined();
    }));

    // Get access token successfull
    it('Get access token success', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', loginResponse);
      expect(service.getAccessToken()).toBeTruthy();
    }));

    // Get access token failure
    it('Get access token failure', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', null);
      expect(service.getAccessToken()).toBeNull();
    }));


    // Get user id successfull
    it('Get user id success', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', loginResponse);
      expect(service.getUserId()).toEqual("1");
    }));

    // Get user id failure
    it('Get user id failure', inject([AuthenticationService], (service: AuthenticationService) => {
      localStorage.setItem('currentUser', null);
      expect(service.getUserId()).toBeNull();
    }));

});
