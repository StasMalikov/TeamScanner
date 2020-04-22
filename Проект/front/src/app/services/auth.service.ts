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
    this.http.post( environment.apiUrl + '/api/v1/auth/registration' , user)
      .subscribe((resp: AuthUser) => {
        localStorage.setItem('auth_token', resp.token);
        localStorage.setItem('login', resp.username);
        //this.router.navigate(['']).then(r => '');
      });
  }
}
