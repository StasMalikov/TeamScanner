import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  logoSrc: string;

  constructor() { }

  ngOnInit() {
    this.logoSrc = 'assets/img/logo_s.jpg';
  }

}
