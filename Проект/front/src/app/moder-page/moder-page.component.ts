import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {StatusUser} from '../models/user/StatusUser';
import {FullEvent} from '../models/event/FullEvent';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {EventService} from '../services/event-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-moder-page',
  templateUrl: './moder-page.component.html',
  styleUrls: ['./moder-page.component.css']
})
export class ModerPageComponent implements OnInit {
  bannedUsers: StatusUser[];
  bannedUsersCount = 0;

  bannedEvents: FullEvent[];
  bannedEventsCount = 0;


  constructor(private auth: AuthService, private http: HttpClient,
              private eventService: EventService, private router: Router) { }

  ngOnInit() {
    this.getBannedEvents();
    this.getBannedUsers();
  }

  getBannedUsers() {
    this.http.post( environment.apiUrl + '/api/v1/moder/ban_listEvents', '', {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: StatusUser[]) => {
        this.bannedUsers = resp;
        if (typeof this.bannedUsers === undefined) {
          this.bannedUsersCount = 0;
        } else {
          this.bannedUsersCount = this.bannedUsers.length;
        }
      }, error => {
        alert('Упс, ошибка');
      });
  }

  getBannedEvents() {
    this.http.post( environment.apiUrl + '/api/v1/moder/ban_listUsers', '', {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: FullEvent[]) => {
        this.bannedEvents = resp;
        if (typeof this.bannedEvents === undefined) {
          this.bannedEventsCount = 0;
        } else {
          this.bannedEventsCount = this.bannedEvents.length;
        }
      }, error => {
        alert('Упс, ошибка');
      });
  }

  getTime(e: FullEvent): string {
    let time = '';
    if (e.dateEvent.getHours() < 10) {
      time = '0' + e.dateEvent.getHours();
    } else {
      time = '' + e.dateEvent.getHours();
    }
    time += ':';
    if (e.dateEvent.getMinutes() < 10) {
      time += '0' + e.dateEvent.getMinutes();
    } else {
      time += '' + e.dateEvent.getMinutes();
    }
    return time;
  }

  detailedEvent(e: FullEvent) {
    this.eventService.setEvent(e);
    this.router.navigate(['event']);
  }

  unlockEvent(fullEvent: FullEvent) {
    
  }
}
