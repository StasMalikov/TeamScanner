import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {AppRoutingModule} from './app-routing.module';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import { SubscriptionsComponent } from './cabinet/subscriptions/subscriptions.component';
import { EventsComponent } from './cabinet/events/events.component';
import { UserDataComponent } from './cabinet/user-data/user-data.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatIconModule} from '@angular/material/icon';
import { CreateEventComponent } from './cabinet/create-event/create-event.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { EventPageComponent } from './event-page/event-page.component';
import { EventSearchComponent } from './event-search/event-search.component';
import { ModerPageComponent } from './moder-page/moder-page.component';
import {MatTabsModule} from '@angular/material/tabs';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { CookieService} from 'ngx-cookie-service';
import { EditEventComponent } from './cabinet/edit-event/edit-event.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomepageComponent,
    LoginPageComponent,
    RegisterPageComponent,
    SubscriptionsComponent,
    EventsComponent,
    UserDataComponent,
    CreateEventComponent,
    EventPageComponent,
    EventSearchComponent,
    ModerPageComponent,
    AdminPageComponent,
    EditEventComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatTabsModule,
  ],
  providers: [
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
