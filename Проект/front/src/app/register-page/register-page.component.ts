import { Component, OnInit } from '@angular/core';
import {RegisterUser} from '../models/registerUser';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

  registerUser: RegisterUser = {
    login: '',
    password: '',
    firstName: '',
    lastName: ''
  };

  confirmPassword: string;

  constructor(private auth: AuthService) { }

  ngOnInit() {}

  register() {
    this.auth.register(this.registerUser);
  }

}
