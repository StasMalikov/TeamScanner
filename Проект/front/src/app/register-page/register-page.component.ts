import { Component, OnInit } from '@angular/core';
import {RegisterUser} from '../models/registerUser';
import {AuthService} from '../services/auth.service';

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

  registerUser: RegisterUser = {
    login: '',
    password: '',
    city: '',
    age: ''
  };

  city: City[] = [
    {value: '0', viewValue: 'Москва'},
    {value: '1', viewValue: 'Санкт-Петербург'},
    {value: '2', viewValue: 'Казань'},
    {value: '2', viewValue: 'Нижний Новгород'},
    {value: '2', viewValue: 'Екатеринбург'}
  ];
  selectedCity: string;
  hide1 = true;
  hide2 = true;

  confirmPassword: string;

  constructor(private auth: AuthService) { }

  ngOnInit() {}

  register() {
    this.auth.register(this.registerUser);
  }

}
