import { Injectable } from '@angular/core';
import {FullEvent} from '../models/event/FullEvent';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor() { }

  event: FullEvent;

  public get getEvent(): FullEvent {
    return new FullEvent();
  }

  public set setEvent(event: FullEvent ) {
    this.event = event;
  }
}
