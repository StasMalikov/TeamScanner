import { Component, OnInit } from '@angular/core';
import {RegisterUser} from '../models/registerUser';
import {AuthService} from '../services/auth.service';
import {FormControl} from '@angular/forms';

interface City {
  value: string;
  viewValue: string;
}

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



  city: City[] = [
    {value: 'Москва', viewValue: 'Москва'},
    {value: 'Санкт-Петербург', viewValue: 'Санкт-Петербург'},
    {value: 'Казань', viewValue: 'Казань'},
    {value: 'Нижний Новгород', viewValue: 'Нижний Новгород'},
    {value: 'Екатеринбург', viewValue: 'Екатеринбург'}
  ];
  selectedCity = '';
  hide1 = true;
  hide2 = true;

  confirmPassword = '';

  constructor(private auth: AuthService) { }

  ngOnInit() {}

  register() {
    if (this.registerUser.login.length < 6) {
      alert('Поле ЛОГИН должно содержать как минимум 6 символов');
      return 0;
    }

    if (this.selectedCity === '') {
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
    this.registerUser.city = this.selectedCity;
    this.auth.register(this.registerUser);
  }

}
