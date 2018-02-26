import { Injectable } from '@angular/core';

@Injectable()
export class UserData {

   constructor() { }

    static users : Object =  {
      "meta": {
        "current_page":1,
        "total_pages": 10
      },
      "users": [
        {
          "id": 100,
          "date_joined": 1519363687246,
          "last_login": 1519363687246,
          "first_name": "John",
          "last_name": "Doe",
          "email": "john.doe@email.com",
          "user_role": "manager"
        },
        {
          "id": 101,
          "date_joined": 1519363687246,
          "last_login": 1519363687246,
          "first_name": "Jane",
          "last_name": "Doe",
          "email": "jane.doe@email.com",
          "user_role": "user"
        }
      ]
    }
     
    static listOfUser() {
      return this.users;
    }
}
