import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../../service/http.service";

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

    constructor(private http: HttpService) {
    }

    ngOnInit(): void {
        this.http.get('users').subscribe(result => {
            console.log(result);
        })
    }
}
