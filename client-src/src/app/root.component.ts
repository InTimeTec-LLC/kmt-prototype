import { Component, OnInit } from '@angular/core';

@Component({
  template: `
    <router-outlet></router-outlet>
  `
})
export class RootComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
