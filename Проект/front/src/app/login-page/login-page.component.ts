import {Component, OnInit } from '@angular/core';
import {SignInUser} from '../models/signInUser';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  authUser: SignInUser = {
    login: '',
    password: ''
  };

  constructor(private auth: AuthService) { }

  ngOnInit() {}

  signIn() {
    this.auth.signIn(this.authUser);
  }
}
