import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {StatusUser} from '../models/user/StatusUser';
import {environment} from '../../environments/environment';
import {FullEvent} from '../models/event/FullEvent';
import {HttpClient} from '@angular/common/http';
import {EventService} from '../services/event-service';
import {Router} from '@angular/router';
import {MiniUser} from '../models/user/MiniUser';
import {FindUser} from '../models/user/FindUser';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {
  users: StatusUser[];
  usersCount = 0;
  userName = '';
  visibleSearchUser = false;
  userFound: StatusUser;
  notFoundUser = false;

  moderators: StatusUser[];
  moderatorCount = 0;
  moderatorName = '';
  visibleSearchModerator = false;
  notFoundModerator = false;
  moderatorFound: StatusUser;

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

        this.visibleSearchUser = false;
        this.notFoundUser = false;
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

        this.visibleSearchModerator = false;
        this.notFoundModerator = false;
      }, error => {
        alert('Упс, ошибка');
      });
  }

  findUsers() {
    const body: FindUser = {
      info: this.userName
    };

    this.http.post( environment.apiUrl + '/api/v1/admin/get_user_by_name', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: StatusUser) => {
        if (resp !== undefined && resp !== null) {
          this.userFound = resp;
          this.visibleSearchUser = true;
        } else {
          this.visibleSearchUser = false;
          this.notFoundUser = true;
        }
      }, error => {
        alert('Упс, ошибка');
      });
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

  findModerator() {
    const body: FindUser = {
      info: this.moderatorName
    };

    this.http.post( environment.apiUrl + '/api/v1/admin/get_moder_by_login', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: StatusUser) => {
        if (resp !== undefined && resp !== null) {
          this.moderatorFound = resp;
          this.visibleSearchModerator = true;
        } else {
          this.visibleSearchModerator = false;
          this.notFoundModerator = true;
        }
      }, error => {
        alert('Упс, ошибка');
      });
  }
}
