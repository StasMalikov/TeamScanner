import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {FullUser} from '../../models/user/FullUser';
import {AuthService} from '../../services/auth.service';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';

interface City {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-user-data',
  templateUrl: './user-data.component.html',
  styleUrls: ['./user-data.component.css']
})
export class UserDataComponent implements OnInit {
  date = new FormControl(new Date());

  fullUser: FullUser = {
    id: 0,
  login: '',
  password: '',
  oldPass: '',
  city: '',
  age: new Date(),
  };

  city: City[] = [
    {value: 'Москва', viewValue: 'Москва'},
    {value: 'Санкт-Петербург', viewValue: 'Санкт-Петербург'},
    {value: 'Казань', viewValue: 'Казань'},
    {value: 'Нижний Новгород', viewValue: 'Нижний Новгород'},
    {value: 'Екатеринбург', viewValue: 'Екатеринбург'}
  ];

  hide1 = true;
  hide2 = true;
  hide3 = true;
  selectedCity = '';

  constructor(private auth: AuthService, private http: HttpClient) {}

  ngOnInit() {

    this.http.get( environment.apiUrl + '/api/v1/users/' + localStorage.getItem('id'), {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: FullUser) => {
        this.fullUser = resp;
        this.fullUser.age = new Date(resp.age);
        this.date = new FormControl(new Date(resp.age));
      }, error => {
        alert('Упс, ошибка');
      });
  }

  saveChanges() {
    this.fullUser.age = new Date(this.date.value);
    this.fullUser.city = this.selectedCity;
    this.http.post( environment.apiUrl + '/api/v1/users/change_user' , this.fullUser , {
        headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: FullUser) => {
        this.fullUser = resp;
        this.fullUser.age = new Date(resp.age);
        this.date = new FormControl(new Date(resp.age));
      }, error => {
        alert('Упс, ошибка');
      });
  }

  updatePWSD() {
    this.http.post( environment.apiUrl + '/api/v1/users/change_user' , this.fullUser , {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: FullUser) => {
        this.fullUser = resp;
        this.fullUser.age = new Date(resp.age);
        this.date = new FormControl(new Date(resp.age));
      }, error => {
        alert('Упс, ошибка');
      });
  }
}
