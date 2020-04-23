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
  selectedCityValue: string;
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
    {value: '0', viewValue: 'Москва'},
    {value: '1', viewValue: 'Санкт-Петербург'},
    {value: '2', viewValue: 'Казань'},
    {value: '3', viewValue: 'Нижний Новгород'},
    {value: '4', viewValue: 'Екатеринбург'}
  ];

  hide1 = true;
  hide2 = true;
  hide3 = true;
  constructor(private auth: AuthService, private http: HttpClient) { }

  ngOnInit() {

    this.http.get( environment.apiUrl + '/api/v1/users/' + localStorage.getItem('id'), {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: FullUser) => {
        this.fullUser = resp;
        this.fullUser.age = new Date(resp.age);
        this.date = new FormControl(new Date(resp.age));
        this.city.push({value: this.city.length.toString(), viewValue: this.fullUser.city});
        this.selectedCityValue = this.city.length.toString();
      }, error => {
        alert('Упс, ошибка');
      });
  }

  saveChanges() {
    console.log(this.selectedCityValue);
  }
}
