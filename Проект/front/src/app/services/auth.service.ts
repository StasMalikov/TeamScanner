import { Injectable } from '@angular/core';
import {SignInUser} from '../models/user/signInUser';
import { Router } from '@angular/router';
import {RegisterUser} from '../models/user/registerUser';
import { environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {AuthUser} from '../models/user/authUser';
import {CookieService} from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private router: Router, private http: HttpClient, private cookie: CookieService) { }

  signIn(user: SignInUser) {
    this.http.post( environment.apiUrl + '/api/v1/auth/login' , user)
      .subscribe((resp: AuthUser) => {
        localStorage.setItem('auth_token', resp.token);
        localStorage.setItem('login', resp.login);
        localStorage.setItem('id', resp.id);
        localStorage.setItem('roles', resp.roles.toString());
        //this.cookie.set('roles', resp.roles.toString());

        this.router.navigate(['']);
      }, error => {
        alert('Неверный логин или пароль');
      });
  }

  logout() {
    //this.cookie.delete('roles');
    this.cookie.deleteAll();
    localStorage.removeItem('roles');
    localStorage.removeItem('auth_token');
    localStorage.removeItem('login');
    localStorage.removeItem('id');
  }

  public get isUser(): boolean {
    if(localStorage.getItem('roles') !== null) {
      const roles = localStorage.getItem('roles').split(',');
      for (let i = 0; i < roles.length; i++) {
        if (roles[i] === 'ROLE_USER') {
          return true;
        }
      }
    }
    return false;
  }

  public get isModerator(): boolean {
    if(localStorage.getItem('roles') !== null) {
      const roles = localStorage.getItem('roles').split(',');
      for (let i = 0; i < roles.length; i++) {
        if (roles[i] === 'ROLE_MODER') {
          return true;
        }
      }
    }
    return false;
  }

  public get isAdministrator(): boolean {
    if(localStorage.getItem('roles') !== null) {
      const roles = localStorage.getItem('roles').split(',');
      for (let i = 0; i < roles.length; i++) {
        if (roles[i] === 'ROLE_ADMIN') {
          return true;
        }
      }
    }
    return false;
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
          localStorage.setItem('id', resp.id);

          localStorage.setItem('a', resp.roles.toString());

          this.router.navigate(['']);
      }, error => {
        alert('Пользователь с таким логином уже существует');
      });
  }

}
