import { TestBed, async } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';
import { User } from '../.././modals/user';
import { Observable } from 'rxjs/Observable';

export function main() {
  describe('Knowledge Base Content Service', () => {

    let kbContentService: UserService;
    let httpMock: HttpTestingController;

    beforeEach(() => {

      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [UserService]
      });

      kbContentService = TestBed.get(UserService);
      httpMock = TestBed.get(HttpTestingController);
    });

    it('should return an Observable when get called', async(() => {
      expect(TestBed.get(UserService).get()).toEqual(jasmine.any(Observable));
    }));

    it('should resolve to list of users when get called', async(() => {

      const expectedUsers = [];

      let actualUser: User[] = [];
      kbContentService.listUser('').subscribe((user: User[]) => {
        actualUser = user;
      });

      httpMock.expectOne('assets/data.json').flush(expectedUsers);

      expect(actualUser).toEqual(expectedUsers);
    }));
  });
}
