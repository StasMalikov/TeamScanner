import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomepageComponent} from './homepage/homepage.component';
import {LoginPageComponent} from './login-page/login-page.component';
import {RegisterPageComponent} from './register-page/register-page.component';
import {SubscriptionsComponent} from './cabinet/subscriptions/subscriptions.component';
import {EventsComponent} from './cabinet/events/events.component';
import {UserDataComponent} from './cabinet/user-data/user-data.component';

const routes: Routes = [
  {path: '', component: HomepageComponent },
  {path: 'login', component: LoginPageComponent },
  {path: 'register', component: RegisterPageComponent },
  {path: 'cabinet/subscriptions', component: SubscriptionsComponent},
  {path: 'cabinet/events', component: EventsComponent},
  {path: 'cabinet/user_data', component: UserDataComponent},
  {path: 'cabinet', component: SubscriptionsComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
