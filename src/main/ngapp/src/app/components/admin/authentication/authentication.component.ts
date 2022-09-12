import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../../service/http.service";

@Component({
    selector: 'app-authentication',
    templateUrl: './authentication.component.html',
    styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit {

    authenticationSelection: string;
    processing: boolean;
    options: string[] = ['DEFAULT', 'LDAP', 'OPEN'];
    enableEdit: boolean;

    constructor(private http: HttpService) {
    }

    ngOnInit(): void {
        this.processing = true;
        this.http.get('settings/AUTHENTICATION_METHOD').subscribe((result: { key, value }) => {
            if (!result || result.key !== 'AUTHENTICATION_METHOD')
                return;

            if (!result.value)
                this.authenticationSelection = 'DEFAULT';
            else
                this.authenticationSelection = result.value;
            this.processing = false;
        }, error => {
            this.processing = false;
        })
    }

    selectionChange(): void {
        if (!this.authenticationSelection)
            return;

        this.http.put('settings', {
            key: 'AUTHENTICATION_METHOD',
            value: this.authenticationSelection
        }).subscribe(result => {
            this.enableEdit = false;
        }, error => {
            this.enableEdit = false;
        })
    }
}
