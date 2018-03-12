import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-knowledge-base-search',
  templateUrl: './knowledge-base-search.component.html',
  styleUrls: ['./knowledge-base-search.component.css']
})
export class KnowledgeBaseSearchComponent implements OnInit {

  searchTxt: string;

  constructor(private router: Router) {
    this.searchTxt = '';
  }

  ngOnInit() {
  }

  onSubmit() {
    this.router.navigate(['knowledge-base'], { queryParams: { search: this.searchTxt }});
  }

}
