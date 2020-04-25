import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {FullEvent} from '../models/event/FullEvent';
import {FormControl} from '@angular/forms';
import {City} from '../models/City';
import {Category} from '../models/Category';
import {FullEventData} from '../models/event/FullEventData';

@Component({
  selector: 'app-event-search',
  templateUrl: './event-search.component.html',
  styleUrls: ['./event-search.component.css']
})
export class EventSearchComponent implements OnInit {
  events: FullEvent[];
  date = new FormControl();
  categorySelected = '';
  citySelected = '';

  city: City[] = [
    {value: 'Москва', viewValue: 'Москва'},
    {value: 'Санкт-Петербург', viewValue: 'Санкт-Петербург'},
    {value: 'Казань', viewValue: 'Казань'},
    {value: 'Нижний Новгород', viewValue: 'Нижний Новгород'},
    {value: 'Екатеринбург', viewValue: 'Екатеринбург'}
  ];

  categories: Category[] = [
    {value: 'Футбол', viewValue: 'Футбол'},
    {value: 'Волейбол', viewValue: 'Волейбол'},
    {value: 'Баскетбол', viewValue: 'Баскетбол'}
  ];

  constructor(private auth: AuthService, private http: HttpClient) { }

  ngOnInit() {
    this.getEvents();
  }



  getEvents() {
    this.http.post( environment.apiUrl + '/api/v1/events/get_events' , '', {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: FullEvent[]) => {
        this.events = resp;
      }, error => {
        alert('Упс, ошибка');
      });
  }

  getSorted() {
    if (new Date(this.date.value).getDate() === new Date(new FormControl().value).getDate()) {
      const body: FullEventData = {
        eventID: 0,
        name: '',
        description: '',
        category: this.categorySelected,
        address: this.citySelected,
        creator_id: 0,
        dateEvent: '',
        participantsCount: 0
      };

      this.http.post( environment.apiUrl + '/api/v1/events/sort_events' , body, {
        headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
      })
        .subscribe((resp: FullEvent[]) => {
          this.events = resp;
        }, error => {
          alert('Упс, ошибка');
        });


    } else {
      const body: FullEvent = {
        eventID: 0,
        name: '',
        description: '',
        category: this.categorySelected,
        address: this.citySelected,
        creator_id: 0,
        dateEvent: new Date(this.date.value),
        participantsCount: 0
      };

      this.http.post( environment.apiUrl + '/api/v1/events/sort_events' , body, {
        headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
      })
        .subscribe((resp: FullEvent[]) => {
          this.events = resp;
        }, error => {
          alert('Упс, ошибка');
        });
    }
  }
}
