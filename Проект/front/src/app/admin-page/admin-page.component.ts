import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {StatusUser} from '../models/user/StatusUser';
import {environment} from '../../environments/environment';
import {FullEvent} from '../models/event/FullEvent';
import {HttpClient} from '@angular/common/http';
import {EventService} from '../services/event-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {
  users: StatusUser[];
  usersCount = 0;
  userName = '';

  constructor(private auth: AuthService, private http: HttpClient,
              private eventService: EventService, private router: Router) { }

  ngOnInit() {
    this.getUsers();
  }

  getUsers() {
    this.http.post( environment.apiUrl + '/api/v1/admin/get_all_users' , '',{
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: StatusUser[]) => {
        this.users = resp;
        if (typeof this.users === undefined) {
          this.usersCount = 0;
        } else {
          this.usersCount = this.users.length;
        }
      }, error => {
        alert('Упс, ошибка');
      });
  }

  findUsers() {
    this.http.post( environment.apiUrl + '/api/v1/admin/get_all_users' , '', {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: StatusUser[]) => {
        this.users = resp;
        if (typeof this.users === undefined) {
          this.usersCount = 0;
        } else {
          this.usersCount = this.users.length;
        }
      }, error => {
        alert('Упс, ошибка');
      });
  }
}
