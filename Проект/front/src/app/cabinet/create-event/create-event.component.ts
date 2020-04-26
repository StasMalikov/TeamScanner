import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {CreateEventClass} from '../../models/event/CreateEventClass';
import {environment} from '../../../environments/environment';
import {FullUser} from '../../models/user/FullUser';
import {AuthService} from '../../services/auth.service';
import {HttpClient} from '@angular/common/http';
import {Category} from '../../models/Category';
import {City} from '../../models/City';

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

  citySelected = '';
  city: City[] = [
    {value: 'Москва', viewValue: 'Москва'},
    {value: 'Санкт-Петербург', viewValue: 'Санкт-Петербург'},
    {value: 'Казань', viewValue: 'Казань'},
    {value: 'Нижний Новгород', viewValue: 'Нижний Новгород'},
    {value: 'Екатеринбург', viewValue: 'Екатеринбург'}
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

  constructor(private auth: AuthService, private http: HttpClient) { }

  ngOnInit() {}

  createEventAction() {
    console.log(this.time);
    this.cEvent.dateEvent = new Date(this.date.value);
    const times = this.time.split(':');
    this.cEvent.dateEvent.setHours(Number(times[0]));
    this.cEvent.dateEvent.setMinutes(Number(times[1]));
    this.cEvent.category = this.selectedCategory;
    this.cEvent.city = this.citySelected;
    this.http.post( environment.apiUrl + '/api/v1/events/add_event' , this.cEvent , {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: any) => {
        alert('Событие успешно создано');
      }, error => {
        alert('Упс, ошибка');
      });
  }
}
