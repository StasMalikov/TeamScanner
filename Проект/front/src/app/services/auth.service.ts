import { Injectable } from '@angular/core';
import {SignInUser} from '../models/user/signInUser';
import { Router } from '@angular/router';
import {RegisterUser} from '../models/user/registerUser';
import { environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {AuthUser} from '../models/user/authUser';
import {FullUser} from '../models/user/FullUser';

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
        localStorage.setItem('id', resp.id);
        this.router.navigate(['']);
      }, error => {
        alert('Неверный логин или пароль');
      });
  }

  logout() {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('login');
    localStorage.removeItem('roles');
    localStorage.removeItem('id');
  }

  public get logIn(): boolean {
    return (localStorage.getItem('auth_token') !== null);
  }

  public get username(): string {
    return localStorage.getItem('login');
  }

  public get id(): string {
    return localStorage.getItem('id');
  }

  register(user: RegisterUser) {
    this.http.post( environment.apiUrl + '/api/v1/auth/registration' , user)
      .subscribe((resp: AuthUser) => {
          localStorage.setItem('auth_token', resp.token);
          localStorage.setItem('login', resp.login);
          localStorage.setItem('roles', resp.roles.toString());
          localStorage.setItem('id', resp.id);
          this.router.navigate(['']);
      }, error => {
        alert('Пользователь с таким логином уже существует');
      });
  }

}
