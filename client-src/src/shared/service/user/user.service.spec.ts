import { TestBed, async, inject} from '@angular/core/testing';
import { HttpModule, Http , Response,  ResponseOptions,  XHRBackend} from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { MockBackend } from '@angular/http/testing';
import { Mock } from 'protractor/built/driverProviders';
import { UserService } from './user.service';
import { AuthenticationService } from '../authentication/authentication.service';

describe('UserService unit tests', () => {

  let userService: UserService;
  let mockBackend: MockBackend;
  let authenticationService: AuthenticationService;

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

    localStorage.setItem('currentUser', loginResponse);

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule, HttpClientModule],
      providers: [UserService, MockBackend,AuthenticationService,{provide: XHRBackend,useClass: MockBackend}]
    });
  });

  describe('Roles', () => {
    const mockResponse = {
      roles: [
                {"id": "5a95506250961bf3bf00565g","role": "User"},
                {"id": "5a95506250961bf3bf00565h","role": "Manager"},
                {"id": "5a95506250961bf3bf00566i","role": "Admin"}
            ]
    };

    it('List roles', inject([UserService, XHRBackend], (userService, mockBackend) => {
      
      mockBackend.connections.subscribe((connection) => {
        connection.mockRespond(new Response(new ResponseOptions({
          body: JSON.stringify(mockResponse)
        })));
      });

      
      userService.listRoles().subscribe((roles) => {
         expect(roles.length).toBe(3);
         
        // expect(videos[0].name).toEqual('Video 0');
        // expect(videos[1].name).toEqual('Video 1');
        // expect(videos[2].name).toEqual('Video 2');
        // expect(videos[3].name).toEqual('Video 3');
      });

    }));
  });
});