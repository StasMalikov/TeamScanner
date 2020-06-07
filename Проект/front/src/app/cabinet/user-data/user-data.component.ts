import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {FullUser} from '../../models/user/FullUser';
import {AuthService} from '../../services/auth.service';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-user-data',
  templateUrl: './user-data.component.html',
  styleUrls: ['./user-data.component.css']
})
export class UserDataComponent implements OnInit {
  date = new FormControl(new Date());

  fullUser: FullUser = {
    id: 0,
    login: '',
    password: '',
    oldPass: '',
    city: '',
    age: new Date(),
  };

  hide1 = true;
  hide2 = true;
  hide3 = true;
  oldPswd = '';
  newPswd = '';
  confirmNewPswd = '';
  allCities: string[] = [];


  constructor(public auth: AuthService, private http: HttpClient) {}

  ngOnInit() {

    this.http.get( environment.apiUrl + '/api/v1/users/' + localStorage.getItem('id'), {
      headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: FullUser) => {
        this.fullUser = resp;
        this.fullUser.age = new Date(resp.age);
        this.date = new FormControl(new Date(resp.age));
      }, error => {
        alert('Упс, ошибка');
      });

    this.getCities();
  }

  selectCity(input: string) {
    this.fullUser.city = input;
  }

  getCities() {
    this.http.post( environment.apiUrl + '/api/v1/users/cites','')
      .subscribe((resp: string[]) => {
        this.allCities = resp;

      }, error => {
        alert('Упс, ошибка');
      });
  }

  saveChanges() {
    this.fullUser.age = new Date(this.date.value);
    this.http.post( environment.apiUrl + '/api/v1/users/change_user' , this.fullUser , {
        headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
    })
      .subscribe((resp: FullUser) => {
        this.fullUser = resp;
        this.fullUser.age = new Date(resp.age);
        this.date = new FormControl(new Date(resp.age));
        alert('Пользователькие данные успешно изменены');
      }, error => {
        alert('Упс, ошибка');
      });
  }

  updatePWSD() {
    if (this.newPswd === this.confirmNewPswd) {
    this.fullUser.oldPass = this.oldPswd;
    this.fullUser.password = this.newPswd;
    this.http.post( environment.apiUrl + '/api/v1/users/change_user' , this.fullUser , {
        headers: {Authorization: 'TSToken_' + localStorage.getItem('auth_token')}
      })
        .subscribe((resp: FullUser) => {
          this.fullUser = resp;
          this.fullUser.age = new Date(resp.age);
          this.date = new FormControl(new Date(resp.age));
          alert('Пароль успешно изменён');
        }, error => {
          alert('Упс, ошибка');
        });

    } else {
      alert('Пароли не совпадают');
    }

  }
}
