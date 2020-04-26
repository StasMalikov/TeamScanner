import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {FullEvent} from '../models/event/FullEvent';
import {FormControl} from '@angular/forms';
import {Category} from '../models/Category';
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
  allCities: string[] = [];
  selectedCityVar = '';

  categories: Category[] = [
    {value: 'Футбол', viewValue: 'Футбол'},
    {value: 'Волейбол', viewValue: 'Волейбол'},
    {value: 'Баскетбол', viewValue: 'Баскетбол'}
  ];

  constructor(private auth: AuthService, private http: HttpClient) { }

  ngOnInit() {
    this.getEvents();
    this.getCities();
  }

  getCities() {
    this.http.post( environment.apiUrl + '/api/v1/users/cites','')
      .subscribe((resp: string[]) => {
        this.allCities = resp;

      }, error => {
        alert('Упс, ошибка');
      });
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

  selectCity(input: string) {
    this.selectedCityVar = input;
  }

  getSorted() {
    if (new Date(this.date.value).getDate() === new Date(new FormControl().value).getDate()) {
      const body: SortEventData = {
        category: this.categorySelected,
        dateEvent: '',
        city: this.selectedCityVar
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
        city: this.selectedCityVar
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
