import {Component, OnInit } from '@angular/core';
import {AuthUser} from '../models/authUser';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  authUser: AuthUser = {
    login: '',
    password: ''
  };

  constructor(private auth: AuthService) { }

  ngOnInit() {}

  signIn() {
    this.auth.signIn(this.authUser);
  }
}
