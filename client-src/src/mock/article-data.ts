import { InMemoryDbService } from 'angular-in-memory-web-api';

export class ArticleData implements InMemoryDbService {
  createDb() {
    const articles =  [
      {
        'id': 1,
        'version': 1,
        'lastModifiedTime': 'Tue Feb 24 17:14:43 IST 2018',
        'lastModifiedBy': {
          'firstName': 'manish',
          'lastName': 'kumar',
          'email': 'manish.2184@gmail.com',
        },
        'createdBy': {
          'firstName': 'Rohan',
          'lastName': 'Kumar',
          'email': 'rohan@test.com',
        },
        'createdTime': 'Tue Feb 27 17:14:43 IST 2018',
        'title': 'string',
        'description': 'html string',
        'articleType' :  {
            'id' : 1,
            'type':  'Type1',
        },
        'restricted': false,
        'needsApproval': true,
        'approver': {
          'id': 2,
          'firstName': 'Manish',
          'lastName': 'Kumar',
          'email': 'manish.2184@gmail.com',
        },
        'approved': true,
         'attachments' : [{
         'id': '1232323454',
        'filename': 'test.jpg',
         'Url' : 'http://localhost/static/test.jpg'
            }]
        },

        {
          'id': 2,
          'version': 1,
          'lastModifiedTime': 'Tue Feb 24 17:14:43 IST 2018',
          'lastModifiedBy': {
            'firstName': 'manish',
            'lastName': 'kumar',
            'email': 'manish.2184@gmail.com',
          },
          'createdBy': {
            'firstName': 'Rohan',
            'lastName': 'Kumar',
            'email': 'rohan@test.com',
            'id': 2,
          },
          'createdTime': 'Tue Feb 27 17:14:43 IST 2018',
          'title': 'string',
          'description': 'html string',
          'articleType' :  {
              'id' : 1,
              'type':  'Type1',
          },
          'restricted': false,
          'needsApproval': true,
          'approver': {
            'id': 2,
            'firstName': 'Manish',
            'lastName': 'Kumar',
            'email': 'manish.2184@gmail.com',
          },
          'approved': true,
           'attachments' : [{
           'id': '1232323454',
          'filename': 'test.jpg',
           'Url' : 'http://localhost/static/test.jpg'
              }]
          },
          {
            'id': 3,
            'version': 1,
            'lastModifiedTime': 'Tue Feb 24 17:14:43 IST 2018',
            'lastModifiedBy': {
              'firstName': 'manish',
              'lastName': 'kumar',
              'email': 'manish.2184@gmail.com',
            },
            'createdBy': {
              'firstName': 'Rohan',
              'lastName': 'Kumar',
              'email': 'rohan@test.com',
            },
            'createdTime': 'Tue Feb 27 17:14:43 IST 2018',
            'title': 'string',
            'description': 'html string',
            'articleType' :  {
                'id' : 1,
                'type':  'Type1',
            },
            'restricted': false,
            'needsApproval': true,
            'approver': {
              'id': 2,
              'firstName': 'Manish',
              'lastName': 'Kumar',
              'email': 'manish.2184@gmail.com',
            },
            'approved': true,
             'attachments' : [{
             'id': '1232323454',
            'filename': 'test.jpg',
             'Url' : 'http://localhost/static/test.jpg'
                }]
            },

            {
              'id': '4',
              'version': 1,
              'lastModifiedTime': 'Tue Feb 24 17:14:43 IST 2018',
              'lastModifiedBy': {
                'firstName': 'manish',
                'lastName': 'kumar',
                'email': 'manish.2184@gmail.com',
              },
              'createdBy': {
                'id': 1,
                'firstName': 'Rohan',
                'lastName': 'Kumar',
                'email': 'rohan@test.com',
              },
              'createdTime': 'Tue Feb 27 17:14:43 IST 2018',
              'title': 'string',
              'description': 'html string',
              'articleType' :  {
                  'id' : 1,
                  'type':  'Type1',
              },
              'restricted': false,
              'needsApproval': true,
              'approver': {
                'id': 2,
                'firstName': 'Manish',
                'lastName': 'Kumar',
                'email': 'manish.2184@gmail.com',
              },
              'approved': true,
               'attachments' : [{
               'id': '1232323454',
              'filename': 'test.jpg',
               'Url' : 'http://localhost/static/test.jpg'
                  }]
              },
              {
                'id': '5',
                'version': 1,
                'lastModifiedTime': 'Tue Feb 24 17:14:43 IST 2018',
                'lastModifiedBy': {
                  'firstName': 'manish',
                  'lastName': 'kumar',
                  'email': 'manish.2184@gmail.com',
                },
                'createdBy': {
                  'id': 2,
                  'firstName': 'Rohan',
                  'lastName': 'Kumar',
                  'email': 'rohan@test.com',
                },
                'createdTime': 'Tue Feb 27 17:14:43 IST 2018',
                'title': 'string',
                'description': 'html string',
                'articleType' :  {
                    'id' : 1,
                    'type':  'Type1',
                },
                'restricted': false,
                'needsApproval': true,
                'approver': {
                  'id': 2,
                  'firstName': 'Manish',
                  'lastName': 'Kumar',
                  'email': 'manish.2184@gmail.com',
                },
                'approved': true,
                 'attachments' : [{
                 'id': '1232323454',
                'filename': 'test.jpg',
                 'Url' : 'http://localhost/static/test.jpg'
                    }]
                }
    ];

    const users = [
      {id: 1, firstName: 'Test1', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'test1@test.com'},
      {id: 2, firstName: 'Test2', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'test2@test.com'},
      {id: 3, firstName: 'Test3', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'test3@test.com'},
      {id: 4, firstName: 'Test4', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'test4@test.com'},
      {id: 5, firstName: 'Test5', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'test5@test.com'},
      {id: 6, firstName: 'Test6', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'Test6@test.com'},
      {id: 7, firstName: 'Test7', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'Test7@test.com'},
      {id: 8, firstName: 'Test8', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'Test8@test.com'},
      {id: 9, firstName: 'Test9', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'Test9@test.com'},
      {id: 10, firstName: 'Test10', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'Test10@test.com'},
      {id: 11, firstName: 'Test11', lastName: 'Kumar', password: '123456',
      userRole: 'manager', email: 'Test11@test.com'}
  ];

  const types = { 'types':  [{id: 1, type: 'Type1'}, {id: 2, type: 'Type2'}, {id: 3, type: 'Type3'}]};

  const approvers = { 'users': [
                              {id: 1, firstName: 'Manish', lastName: 'Tripathi'},
                              {id: 2, firstName: 'Harish', lastName: 'Patidar'},
                              {id: 3, firstName: 'Rohan', lastName: 'Rogers'}
                    ]
                  };


    return {articles, types, approvers};
  }
}
