import { Component, OnInit } from '@angular/core';
import {RegisterUser} from '../models/user/registerUser';
import {AuthService} from '../services/auth.service';
import {FormControl} from '@angular/forms';
import {environment} from '../../environments/environment';
import {FullEvent} from '../models/event/FullEvent';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

  date = new FormControl();

  registerUser: RegisterUser = {
    login: '',
    password: '',
    city: '',
    age: new Date()
  };

  hide1 = true;
  hide2 = true;

  allCities: string[] = [];
  confirmPassword = '';

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


  register() {
    if (this.registerUser.login.length < 6) {
      alert('Поле ЛОГИН должно содержать как минимум 6 символов');
      return 0;
    }

    if (this.registerUser.city === '') {
      alert('Укажите ваш населённый пункт');
      return 0;
    }


    if (this.date.value == null) {
      alert('Укажите дату рождения');
      return 0;
    }


    if (this.registerUser.password.length < 6 || this.confirmPassword.length < 6) {
      alert('Пароль доджен быть длиннее 6 символов');
      return 0;
    }

    if (this.registerUser.password.length !== this.confirmPassword.length) {
      alert('Пароли не совпадают');
      return 0;
    }

    this.registerUser.age = new Date(this.date.value);
    this.auth.register(this.registerUser);
  }

  selectCity(input: string) {
    this.registerUser.city = input;
  }
}
