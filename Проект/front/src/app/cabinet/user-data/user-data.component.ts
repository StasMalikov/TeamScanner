import { Component, OnInit } from '@angular/core';

interface City {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-user-data',
  templateUrl: './user-data.component.html',
  styleUrls: ['./user-data.component.css']
})
export class UserDataComponent implements OnInit {
  selectedCity: string;

  city: City[] = [
    {value: '0', viewValue: 'Москва'},
    {value: '1', viewValue: 'Санкт-Петербург'},
    {value: '2', viewValue: 'Казань'},
    {value: '2', viewValue: 'Нижний Новгород'},
    {value: '2', viewValue: 'Екатеринбург'}
  ];

  hide1 = true;
  hide2 = true;
  hide3 = true;
  constructor() { }

  ngOnInit() {
  }

}
