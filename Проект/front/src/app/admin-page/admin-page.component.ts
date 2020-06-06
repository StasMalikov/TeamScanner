import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {StatusUser} from '../models/user/StatusUser';
import {environment} from '../../environments/environment';
import {FullEvent} from '../models/event/FullEvent';
import {HttpClient} from '@angular/common/http';
import {EventService} from '../services/event-service';
import {Router} from '@angular/router';
import {MiniUser} from '../models/user/MiniUser';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {
  users: StatusUser[];
  usersCount = 0;
  userName = '';

  moderators: StatusUser[];
  moderatorCount = 0;
  moderatorName = '';

  constructor(private auth: AuthService, private http: HttpClient,
              private eventService: EventService, private router: Router) { }

  ngOnInit() {
    this.getUsers();
    this.getModerators();
  }

  getUsers() {
    this.http.post( environment.apiUrl + '/api/v1/admin/get_all_users', '', {
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

  getModerators() {
    this.http.post( environment.apiUrl + '/api/v1/admin/get_all_moders', '', {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: StatusUser[]) => {
        this.moderators = resp;
        if (typeof this.moderators === undefined) {
          this.moderatorCount = 0;
        } else {
          this.moderatorCount = this.moderators.length;
        }
      }, error => {
        alert('Упс, ошибка');
      });
  }

  findUsers() {
    // this.http.post( environment.apiUrl + '/api/v1/admin/get_all_users' , '', {
    //   headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    // })
    //   .subscribe((resp: StatusUser[]) => {
    //     this.users = resp;
    //     if (typeof this.users === undefined) {
    //       this.usersCount = 0;
    //     } else {
    //       this.usersCount = this.users.length;
    //     }
    //   }, error => {
    //     alert('Упс, ошибка');
    //   });
  }

  addModerator(statusUser: StatusUser) {
    const body: MiniUser = {
      userName: statusUser.login,
      status: 'moder'
    };

    this.http.post( environment.apiUrl + '/api/v1/admin/set_role', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: any) => {
        this.getUsers();
        this.getModerators();
      }, error => {
        alert('Упс, ошибка');
      });
  }

  removeModerator(statusUser: StatusUser) {
    const body: MiniUser = {
      userName: statusUser.login,
      status: 'moder'
    };

    this.http.post( environment.apiUrl + '/api/v1/admin/rem_role', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: any) => {
        this.getUsers();
        this.getModerators();
      }, error => {
        alert('Упс, ошибка');
      });
  }
}
