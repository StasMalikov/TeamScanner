import { Injectable } from '@angular/core';
import {AuthUser} from '../models/authUser';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authUser: AuthUser = {
    login: 'dd',
    password: ''
  };

  constructor(private router: Router) { }

  signIn(user: AuthUser) {
    this.authUser = user;
    localStorage.setItem('login', user.login);
    localStorage.setItem('auth_token', 'dsfdsf');
    this.router.navigate(['']);
  }

  logout() {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('login');
  }

  public get logIn(): boolean {
    return (localStorage.getItem('auth_token') !== null);
  }

  public get username(): string {
    return localStorage.getItem('login');
  }
}
