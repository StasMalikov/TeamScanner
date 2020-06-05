import { Component, OnInit } from '@angular/core';
import {EventService} from '../services/event-service';
import {FullEvent} from '../models/event/FullEvent';
import {FormControl} from '@angular/forms';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {MiniEvent} from '../models/event/MiniEvent';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {

  constructor(private eventService: EventService, private http: HttpClient, private auth: AuthService) { }

  event: FullEvent;
  subscribe = false;
  date = new FormControl(new Date());

  ngOnInit() {
    this.event = this.eventService.getEvent;
  }

  subscribeOn() {
    const body: MiniEvent = {
      userName: this.auth.username,
      eventID: this.eventService.eventId
    };

    this.http.post( environment.apiUrl + '/api/v1/events/subscribe', body)
      .subscribe((resp: string[]) => {
        this.subscribe = !this.subscribe;
      }, error => {
        alert('Упс, ошибка');
      });
  }

  subscribeOff() {
    const body: MiniEvent = {
      userName: this.auth.username,
      eventID: this.eventService.eventId
    };

    this.http.post( environment.apiUrl + '/api/v1/events/unsubscribe', body)
      .subscribe((resp: string[]) => {
        this.subscribe = !this.subscribe;
      }, error => {
        alert('Упс, ошибка');
      });

  }

}
