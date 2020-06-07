import { Component, OnInit } from '@angular/core';
import {FullEvent} from '../../models/event/FullEvent';
import {AuthService} from '../../services/auth.service';
import {HttpClient} from '@angular/common/http';
import {EventService} from '../../services/event-service';
import {Router} from '@angular/router';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {
  events: FullEvent[];
  length = 0;

  constructor(public auth: AuthService, private http: HttpClient,  private eventService: EventService, private router: Router) { }

  ngOnInit() {
    this.getEvents();
  }

  getEvents() {
    this.http.get( environment.apiUrl + '/api/v1/events/get_events_where_user_exist/' + this.auth.id)
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

  detailedEvent(e: FullEvent) {
    this.eventService.setEvent(e);
    this.router.navigate(['event']);
  }

}
