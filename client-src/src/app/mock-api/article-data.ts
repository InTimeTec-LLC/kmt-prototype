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
    return {articles};
  }
}
