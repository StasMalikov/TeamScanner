import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {AppRoutingModule} from './app-routing.module';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import {FormsModule} from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import { SubscriptionsComponent } from './cabinet/subscriptions/subscriptions.component';
import { EventsComponent } from './cabinet/events/events.component';
import { UserDataComponent } from './cabinet/user-data/user-data.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomepageComponent,
    LoginPageComponent,
    RegisterPageComponent,
    SubscriptionsComponent,
    EventsComponent,
    UserDataComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        BrowserAnimationsModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
