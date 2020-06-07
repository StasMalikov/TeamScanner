import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {StatusUser} from '../models/user/StatusUser';
import {FullEvent} from '../models/event/FullEvent';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {EventService} from '../services/event-service';
import {Router} from '@angular/router';
import {EventStatus} from '../models/event/EventStatus';
import {UserStatus} from '../models/user/UserStatus';
import {FindUser} from '../models/user/FindUser';
import {FindByName} from '../models/user/FindByName';

@Component({
  selector: 'app-moder-page',
  templateUrl: './moder-page.component.html',
  styleUrls: ['./moder-page.component.css']
})
export class ModerPageComponent implements OnInit {
  bannedUsers: StatusUser[];
  bannedUsersCount = 0;

  bannedEvents: FullEvent[];
  bannedEventsCount = 0;

  userName = '';
  foundUser: StatusUser;
  seeFoundUser = false;
  foundUserNotification = false;

  eventName = '';
  foundEvents: FullEvent[];
  foundEventsCount = 0;
  seeFoundEvents = false;

  constructor(private auth: AuthService, private http: HttpClient,
              private eventService: EventService, private router: Router) { }

  ngOnInit() {
    this.getBannedEvents();
    this.getBannedUsers();
  }

  getBannedUsers() {
    this.http.post( environment.apiUrl + '/api/v1/moder/ban_listUsers', '', {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: StatusUser[]) => {
        this.bannedUsers = resp;
        if (typeof this.bannedUsers === undefined) {
          this.bannedUsersCount = 0;
        } else {
          if (this.bannedUsers != null) {
            this.bannedUsersCount = this.bannedUsers.length;
          }
        }
      }, error => {
        alert('Упс, ошибка');
      });
  }

  getBannedEvents() {
    this.http.post( environment.apiUrl + '/api/v1/moder/ban_listEvents', '', {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: FullEvent[]) => {
        this.bannedEvents = resp;
        if (typeof this.bannedEvents === undefined) {
          this.bannedEventsCount = 0;
        } else {
          if (this.bannedEvents != null) {
            this.bannedEventsCount = this.bannedEvents.length;
          }
        }
      }, error => {
        alert('Упс, ошибка');
      });
  }

  detailedEvent(e: FullEvent) {
    this.eventService.setEvent(e);
    this.router.navigate(['event']);
  }

  unlockEvent(fullEvent: FullEvent) {
    const body: EventStatus = {
      eventID: fullEvent.eventID,
      status: 'ACTIVE'
    };

    fullEvent.status = 'ACTIVE';

    this.http.post( environment.apiUrl + '/api/v1/moder/set_eventStatus', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: any) => {
        this.getBannedEvents();
        this.foundEventsCount = 0;
        this.seeFoundEvents = false;
      }, error => {
        alert('Упс, ошибка');
      });
  }

  lockEvent(fullEvent: FullEvent) {
    const body: EventStatus = {
      eventID: fullEvent.eventID,
      status: 'BANNED'
    };
    fullEvent.status = 'BANNED';

    this.http.post( environment.apiUrl + '/api/v1/moder/set_eventStatus', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: any) => {
        this.getBannedEvents();
        this.foundEventsCount = 0;
        this.seeFoundEvents = false;
      }, error => {
        alert('Упс, ошибка');
      });
  }

  unlockUser(user: StatusUser) {
    const body: UserStatus = {
      userName: user.login,
      status: 'ACTIVE'
    };

    this.http.post( environment.apiUrl + '/api/v1/moder/set_userStatus', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: any) => {
        this.getBannedUsers();
        this.seeFoundUser = false;
        this.foundUserNotification = false;
      }, error => {
        alert('Упс, ошибка');
      });
  }

  lockUser(user: StatusUser) {
    const body: UserStatus = {
      userName: user.login,
      status: 'BANNED'
    };

    this.http.post( environment.apiUrl + '/api/v1/moder/set_userStatus', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: any) => {
        this.getBannedUsers();
        this.seeFoundUser = false;
        this.foundUserNotification = false;
      }, error => {
        alert('Упс, ошибка');
      });
  }

  findUser() {
    const body: FindByName = {
      name: this.userName
    };

    this.http.post( environment.apiUrl + '/api/v1/moder/usersByName', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: StatusUser) => {
        // if (resp !== undefined && resp !== null) {
          this.foundUser = resp;
          this.seeFoundUser = true;
          this.foundUserNotification = false;
        // } else {
        //   this.seeFoundUser = false;
        //   this.foundUserNotification = true;
        // }
      }, error => {
        this.seeFoundUser = false;
        this.foundUserNotification = true;
      });
  }

  findEvent() {
    const body: FindByName = {
      name: this.eventName
    };

    this.http.post( environment.apiUrl + '/api/v1/moder/eventsByName', body, {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: FullEvent[]) => {
        this.foundEvents = resp;
        if (this.foundEvents != null) {
          this.foundEventsCount = this.foundEvents.length;
        }
        this.seeFoundEvents = false;
      }, error => {
        this.foundEventsCount = 0;
        this.seeFoundEvents = true;
      });
  }

  public  isBannedUser(user: StatusUser): boolean {
    if (user.status === 'ACTIVE') {
      return false;
    }
    return true;
  }

  public  isBannedEvent(event: FullEvent): boolean {
    if (event.status === 'ACTIVE') {
      return false;
    }
    return true;
  }

}
