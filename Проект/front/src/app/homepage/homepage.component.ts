import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  constructor() { }

  football: string;
  hockey: string;
  basketball: string;
  volleyball: string;

  ngOnInit() {
    this.football = 'assets/img/football.jpg';
    this.hockey = 'assets/img/hockey.jpg';
    this.basketball = 'assets/img/basketball.jpg';
    this.volleyball = 'assets/img/volleyball.jpg';
  }

}
