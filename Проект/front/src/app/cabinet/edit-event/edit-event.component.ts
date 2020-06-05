import { Component, OnInit } from '@angular/core';
import {EventService} from '../../services/event-service';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../services/auth.service';
import {FullEvent} from '../../models/event/FullEvent';
import {FormControl} from '@angular/forms';
import {Category} from '../../models/Category';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-edit-event',
  templateUrl: './edit-event.component.html',
  styleUrls: ['./edit-event.component.css']
})
export class EditEventComponent implements OnInit {
  categories: Category[] = [
    {value: 'Футбол', viewValue: 'Футбол'},
    {value: 'Волейбол', viewValue: 'Волейбол'},
    {value: 'Баскетбол', viewValue: 'Баскетбол'},
    {value: 'Хоккей', viewValue: 'Хоккей'}
  ];

  event: FullEvent;
  date = new FormControl(new Date());
  allCities: string[] = [];
  selectedCategory: string;
  time: string;

  constructor(private eventService: EventService, private http: HttpClient, private auth: AuthService) { }

  ngOnInit() {
    this.event = this.eventService.getEvent;
    this.getCities();
    this.date = new FormControl(new Date(this.event.dateEvent));
    this.setTime();
    this.selectedCategory = this.event.category;
  }

  setTime() {
    if (this.event.dateEvent.getHours() < 10) {
      this.time = '0' + this.event.dateEvent.getHours();
    } else {
      this.time = '' + this.event.dateEvent.getHours();
    }
    this.time += ':';
    if (this.event.dateEvent.getMinutes() < 10) {
      this.time += '0' + this.event.dateEvent.getMinutes();
    } else {
      this.time += '' + this.event.dateEvent.getMinutes();
    }
  }

  getCities() {
    this.http.post( environment.apiUrl + '/api/v1/users/cites','')
      .subscribe((resp: string[]) => {
        this.allCities = resp;

      }, error => {
        alert('Упс, ошибка');
      });
  }

  saveEvent() {
    this.event.dateEvent = new Date(this.date.value);
    const times = this.time.split(':');
    this.event.dateEvent.setHours(Number(times[0]));
    this.event.dateEvent.setMinutes(Number(times[1]));
    this.event.category = this.selectedCategory;

    this.http.post( environment.apiUrl + '/api/v1/events/change_event' , this.event , {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: any) => {
        alert('Событие успешно отредактировано');
      }, error => {
        alert('Упс, ошибка');
      });
  }

  selectCity(city: string) {
    this.event.city = city;
  }

  changeCategory(value: string) {
    this.event.category = value;

  }
}
