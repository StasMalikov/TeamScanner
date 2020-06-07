import { Component, OnInit } from '@angular/core';
import {FullEvent} from '../../models/event/FullEvent';
import {environment} from '../../../environments/environment';
import {AuthService} from '../../services/auth.service';
import {HttpClient} from '@angular/common/http';
import {EventService} from '../../services/event-service';
import {Router} from '@angular/router';
// @ts-ignore
import {MiniEvent} from '../../models/event/MiniEvent';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  events: FullEvent[];
  length = 0;

  constructor(public auth: AuthService, private http: HttpClient,  private eventService: EventService, private router: Router) { }

  ngOnInit() {
    this.getEvents();
  }

  detailedEvent(e: FullEvent) {
    this.eventService.setEvent(e);
    this.router.navigate(['event']);
  }

  editEvent(e: FullEvent) {
    this.eventService.setEvent(e);
    this.router.navigate(['edit_event']);
  }

  getEvents() {
    this.http.get( environment.apiUrl + '/api/v1/events/get_creator_events/' + this.auth.id)
      .subscribe((resp: FullEvent[]) => {
        this.events = resp;
        if (typeof this.events === undefined) {
          this.length = 0;
        } else {
          this.length = this.events.length;
        }
      }, error => {
        alert('Упс, ошибка');
      });
  }

  deleteEvent(e: FullEvent) {
    const body: MiniEvent = {
      userName: this.auth.username,
      eventID: e.eventID
    };

    this.http.post( environment.apiUrl + '/api/v1/events/rem_event' , body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: any) => {
        this.getEvents();
      }, error => {
        alert('Упс, ошибка');
      });
  }

}
