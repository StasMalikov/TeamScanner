import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {HttpClient} from '@angular/common/http';
import {EventService} from '../services/event-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  constructor(private eventService: EventService, private router: Router) { }

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

  footballSort(){
  this.eventService.setEventSort('Футбол');
  this.router.navigate(['event_search']);
  }

  hockeySort(){
    this.eventService.setEventSort('Хоккей');
    this.router.navigate(['event_search']);
  }

  basketballSort(){
    this.eventService.setEventSort('Баскетбол');
    this.router.navigate(['event_search']);
  }

  volleyballSort(){
    this.eventService.setEventSort('Волейбол');
    this.router.navigate(['event_search']);
  }

}
