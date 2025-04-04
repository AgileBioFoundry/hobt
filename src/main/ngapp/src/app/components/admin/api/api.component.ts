import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../../service/http.service";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-api',
    templateUrl: './api.component.html',
    standalone: true,
    imports: [
        FormsModule,
        NgIf,
        NgbTooltip
    ],
    styleUrls: ['./api.component.css']
})
export class ApiComponent implements OnInit {

    editMode: boolean;
    apiToken: string;

    constructor(private http: HttpService) {
    }

    ngOnInit(): void {
        // retrieve the current api token
        this.http.get('settings/PROTOCOL_IO_API_TOKEN').subscribe((result: any) => {
            console.log(result);
            this.apiToken = result.value;
        });
    }

    updateToken(): void {
        this.http.put('settings', {key: 'PROTOCOL_IO_API_TOKEN', value: this.apiToken}).subscribe(() => {
            this.editMode = false;
        });
    }
}
