import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {CreateEventClass} from '../../models/CreateEventClass';

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
    {value: '2', viewValue: 'Баскетбол'}
  ];
  selectedCategory: string;
  date = new FormControl();

  cEvent: CreateEventClass = {
    name: '',
    description: '',
    category: '',
    address: '',
    creator_id: 0,
    dateEvent: new Date(),
    participantsCount: 1
  };

  constructor() { }

  ngOnInit() {
  }

  createEventAction() {

  }
}
