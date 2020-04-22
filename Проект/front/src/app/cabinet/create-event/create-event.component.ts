import { Component, OnInit } from '@angular/core';

interface Category {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit {
  categories: Category[] = [
    {value: '0', viewValue: 'Футбол'},
    {value: '1', viewValue: 'Волейбол'},
    {value: '2', viewValue: 'Баскетбол'},
    {value: '2', viewValue: 'Хоккей'}
  ];
  selectedCategory: string;
  constructor() { }

  ngOnInit() {
  }

}
