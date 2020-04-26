import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {CreateEventClass} from '../../models/event/CreateEventClass';
import {environment} from '../../../environments/environment';
import {AuthService} from '../../services/auth.service';
import {HttpClient} from '@angular/common/http';
import {Category} from '../../models/Category';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit {
  categories: Category[] = [
    {value: 'Футбол', viewValue: 'Футбол'},
    {value: 'Волейбол', viewValue: 'Волейбол'},
    {value: 'Баскетбол', viewValue: 'Баскетбол'}
  ];

  selectedCategory: string;
  date = new FormControl();
  time: string;
  cEvent: CreateEventClass = {
    name: '',
    description: '',
    category: '',
    address: '',
    creator_id: Number(this.auth.id),
    dateEvent: new Date(),
    participantsCount: 1,
    city: ''
  };
  allCities: string[] = [];

  constructor(private auth: AuthService, private http: HttpClient) { }

  ngOnInit() {
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

  createEventAction() {
    this.cEvent.dateEvent = new Date(this.date.value);
    const times = this.time.split(':');
    this.cEvent.dateEvent.setHours(Number(times[0]));
    this.cEvent.dateEvent.setMinutes(Number(times[1]));
    this.cEvent.category = this.selectedCategory;
    this.http.post( environment.apiUrl + '/api/v1/events/add_event' , this.cEvent , {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: any) => {
        alert('Событие успешно создано');
      }, error => {
        alert('Упс, ошибка');
      });
  }

  selectCity(input: string) {
    this.cEvent.city = input;
  }
}
