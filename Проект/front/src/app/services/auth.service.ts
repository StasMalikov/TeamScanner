import { Injectable } from '@angular/core';
import {AuthUser} from '../models/authUser';
import { Router } from '@angular/router';
import {RegisterUser} from '../models/registerUser';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) { }

  signIn(user: AuthUser) {
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

  register(user: RegisterUser) {
    localStorage.setItem('login', user.login);
    localStorage.setItem('auth_token', 'dsfdsf');
    this.router.navigate(['']);
    //код регистрации
  }
}
