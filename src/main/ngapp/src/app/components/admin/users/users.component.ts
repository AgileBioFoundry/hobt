import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../../service/http.service";
import {Paging} from "../../../model/paging.model";
import {User} from "../../../model/user.model";

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

    paging: Paging;
    users: User[];

    constructor(private http: HttpService) {
        this.paging = new Paging();
    }

    ngOnInit(): void {
        this.http.get('users', this.paging).subscribe((result: any) => {
            console.log(result);
            this.users = result.requested;
            this.paging.available = result.available;
        }, error => {
            console.log(error);
        });
    }
}
