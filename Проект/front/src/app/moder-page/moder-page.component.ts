import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-moder-page',
  templateUrl: './moder-page.component.html',
  styleUrls: ['./moder-page.component.css']
})
export class ModerPageComponent implements OnInit {

  constructor(private auth: AuthService) { }

  ngOnInit() {}

}
