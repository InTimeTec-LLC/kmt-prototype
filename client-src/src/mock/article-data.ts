import { InMemoryDbService } from 'angular-in-memory-web-api';

export class ArticleData implements InMemoryDbService {
  createDb() {
    const articles = [
        {id: 1, title: 'Test Title1', description: 'Test Description1'},
        {id: 2, title: 'Test Title2', description: 'Test Description2'},
        {id: 3, title: 'Test Title3', description: 'Test Description3'},
        {id: 4, title: 'Test Title4', description: 'Test Description4'},
        {id: 5, title: 'Test Title5', description: 'Test Description5'}
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
      userRole: 'manager', email: 'test5@test.com'}
  ];

    return {articles, users};
  }
}
