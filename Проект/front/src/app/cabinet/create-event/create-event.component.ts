import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {CreateEventClass} from '../../models/CreateEventClass';
import {environment} from '../../../environments/environment';
import {FullUser} from '../../models/user/FullUser';
import {AuthService} from '../../services/auth.service';
import {HttpClient} from '@angular/common/http';

interface Category {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit {
  categories: Category[] = [
    {value: '0', viewValue: 'Футбол'},
    {value: '1', viewValue: 'Волейбол'},
    {value: '2', viewValue: 'Баскетбол'}
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
    participantsCount: 1
  };

  constructor(private auth: AuthService, private http: HttpClient) { }

  ngOnInit() {
  }

  createEventAction() {
    console.log(this.time);
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
}
