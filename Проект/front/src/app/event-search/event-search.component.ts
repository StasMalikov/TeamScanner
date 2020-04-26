import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {FullEvent} from '../models/event/FullEvent';
import {FormControl} from '@angular/forms';
import {City} from '../models/City';
import {Category} from '../models/Category';
import {FullEventData} from '../models/event/FullEventData';
import {SortEventData} from '../models/event/SortEventData';
import {SortEvent} from '../models/event/SortEvent';

@Component({
  selector: 'app-event-search',
  templateUrl: './event-search.component.html',
  styleUrls: ['./event-search.component.css']
})
export class EventSearchComponent implements OnInit {
  events: FullEvent[];
  date = new FormControl();
  categorySelected = '';
  length = 0;

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
        if (typeof this.events === undefined) {
          this.length = 0;
        } else {
          this.length = this.events.length;
        }
      }, error => {
        alert('Упс, ошибка');
      });
  }

  getSorted() {
    if (new Date(this.date.value).getDate() === new Date(new FormControl().value).getDate()) {
      const body: SortEventData = {
        category: this.categorySelected,
        dateEvent: '',
        city: this.citySelected
      };

      this.http.post( environment.apiUrl + '/api/v1/events/sort_events' , body, {
        headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
      })
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


    } else {
      const body: SortEvent = {
        category: this.categorySelected,
        dateEvent: new Date(this.date.value),
        city: this.citySelected
      };

      this.http.post( environment.apiUrl + '/api/v1/events/sort_events' , body, {
        headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
      })
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
  }
}
