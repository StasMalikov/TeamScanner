import { Component, OnInit } from '@angular/core';
import {EventService} from '../services/event-service';
import {FullEvent} from '../models/event/FullEvent';
import {FormControl} from '@angular/forms';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
// @ts-ignore
import {MiniEvent} from '../models/event/MiniEvent';
import {AuthService} from '../services/auth.service';
import {CommentNew} from '../models/comment/CommentNew';

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
  commentText = '';

  ngOnInit() {
    this.event = this.eventService.getEvent;
  }

  subscribeOn() {
    const body: MiniEvent = {
      userName: this.auth.username,
      eventID: this.eventService.eventId
    };

    this.http.post( environment.apiUrl + '/api/v1/events/subscribe', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
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

    this.http.post( environment.apiUrl + '/api/v1/events/unsubscribe', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: string[]) => {
        this.subscribe = !this.subscribe;
      }, error => {
        alert('Упс, ошибка');
      });

  }

  addComment() {
    const body: CommentNew = {
      userName: this.auth.username,
      comment: this.commentText,
      commentID: 0,
      eventID: this.eventService.eventId
    };

    this.http.post( environment.apiUrl + '/api/v1/comments/add_comment', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: string[]) => {
        alert('комментарий добавлен');
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

  isCreator(): boolean {
    if (this.event.creator_id === Number(this.auth.id)) {
      return true;
    }
    return false;
  }
}
