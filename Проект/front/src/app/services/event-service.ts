import { Injectable } from '@angular/core';
import {FullEvent} from '../models/event/FullEvent';
import {CookieService} from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private cookie: CookieService) { }

  public get getEvent(): FullEvent {
    const e: FullEvent = {
      eventID: Number(this.cookie.get('event_id')),
      name: this.cookie.get('event_name'),
      description: this.cookie.get('event_description'),
      category: this.cookie.get('event_category'),
      address: this.cookie.get('event_address'),
      dateEvent: new Date(this.cookie.get('event_date')),
      participantsCount: Number(this.cookie.get('event_participantsCount')),
      city: this.cookie.get('event_city'),
      creator_id: Number(this.cookie.get('event_creator_id')),
      nameCreator: this.cookie.get('nameCreator'),
      status: this.cookie.get('status'),
    };
    return e;
  }

  public get eventId(): number {
    return Number(this.cookie.get('event_id'));
  }

  setEventSort(sort: string) {
    this.cookie.set('event_sort', sort);
  }

  getEventSort(): string {
    return this.cookie.get('event_sort');
  }

  clearEventSort() {
    this.cookie.delete('event_sort');
  }

  setEvent(e: FullEvent ) {
    const a = new Date(e.dateEvent);
    this.cookie.set('event_id', String(e.eventID));
    this.cookie.set('event_name', e.name);
    this.cookie.set('event_description', e.description);
    this.cookie.set('event_category', e.category);
    this.cookie.set('event_address', e.address);
    this.cookie.set('event_date', a.toISOString());
    this.cookie.set('event_participantsCount', String(e.participantsCount));
    this.cookie.set('event_city', e.city);
    this.cookie.set('event_creator_id', String(e.creator_id));
    this.cookie.set('nameCreator', e.nameCreator);
    this.cookie.set('status', e.status);
  }
}
