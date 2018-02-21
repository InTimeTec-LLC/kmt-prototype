import { Component, OnInit, ViewEncapsulation } from '@angular/core';
//import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DashboardComponent implements OnInit {

  knowledge_base_contents: any;

  constructor() {
    //private http: HttpClient 
  }

  ngOnInit() {
    /*this.http.get('/dashboard').subscribe(data => {
      console.log(data);
      this.knowledge_base_content = data;
    });*/
        this.knowledge_base_contents = [{"id":1, "title":"Test Title", "description":"Test Description"}, 
    {"id":1, "title":"Test Title", "description":"Test Description"}, {"id":1, "title":"Test Title", "description":"Test Description"}]

  }

}
