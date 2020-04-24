import {Component, OnInit } from '@angular/core';
import {SignInUser} from '../models/user/signInUser';
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
hide = true;
  constructor(private auth: AuthService) { }

  ngOnInit() {}

  signIn() {
    if (this.authUser.login.length < 6) {
      alert('Длина логина должна быть больше 6 символов');
      return 0;
    }

    if (this.authUser.password.length < 6) {
      alert('Длина пароля должна быть больше 6 символов');
      return 0;
    }

    this.auth.signIn(this.authUser);
  }
}
