import { Component, OnInit } from '@angular/core';
import {EventService} from '../services/event-service';
import {FullEvent} from '../models/event/FullEvent';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {

  constructor(private eventService: EventService) { }

  event: FullEvent;
  subscribe = false;
  date = new FormControl(new Date());

  ngOnInit() {
    this.event = this.eventService.getEvent;
  }

  subscribeOn() {
    this.subscribe = !this.subscribe;
  }
}
