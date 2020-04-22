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
    age: new Date()
  };

  city: City[] = [
    {value: 'Москва', viewValue: 'Москва'},
    {value: 'Санкт-Петербург', viewValue: 'Санкт-Петербург'},
    {value: 'Казань', viewValue: 'Казань'},
    {value: 'Нижний Новгород', viewValue: 'Нижний Новгород'},
    {value: 'Екатеринбург', viewValue: 'Екатеринбург'}
  ];
  selectedCity: string;
  hide1 = true;
  hide2 = true;

  confirmPassword: string;

  constructor(private auth: AuthService) { }

  ngOnInit() {}

  register() {
    this.registerUser.city = this.selectedCity;
    this.auth.register(this.registerUser);
  }

}
