import { Injectable } from '@angular/core';
import {SignInUser} from '../models/signInUser';
import { Router } from '@angular/router';
import {RegisterUser} from '../models/registerUser';
import { environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {AuthUser} from '../models/authUser';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router, private http: HttpClient) { }

  signIn(user: SignInUser) {
    this.http.post( environment.apiUrl + '/api/v1/auth/login' , user)
      .subscribe((resp: AuthUser) => {
        localStorage.setItem('auth_token', resp.token);
        localStorage.setItem('login', resp.login);
        localStorage.setItem('roles', resp.roles.toString());
        this.router.navigate(['']);
      }, error => {
        alert('Неверный логин или пароль');
      });
  }

  logout() {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('login');
    localStorage.removeItem('roles');

  }

  public get logIn(): boolean {
    return (localStorage.getItem('auth_token') !== null);
  }

  public get username(): string {
    return localStorage.getItem('login');
  }

  register(user: RegisterUser) {
    this.http.post( environment.apiUrl + '/api/v1/auth/registration' , user)
      .subscribe((resp: AuthUser) => {
          localStorage.setItem('auth_token', resp.token);
          localStorage.setItem('login', resp.login);
          localStorage.setItem('roles', resp.roles.toString());
          this.router.navigate(['']);
      }, error => {
        alert('Пользователь с таким логином уже существует');
      });
  }
}
