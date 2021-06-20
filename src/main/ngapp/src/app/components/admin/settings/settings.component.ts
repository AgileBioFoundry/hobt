import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../../service/http.service";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

    active: string;

    constructor(private http: HttpService, private route: ActivatedRoute, private location: Location) {
    }

    ngOnInit(): void {
        const param = this.route.snapshot.params['subsection'];
        console.log(param);

        if (param)
            this.active = param;
        else
            this.active = 'tiers';
    }

    goTo(location: string): void {
        this.location.go('/admin/' + location);
        this.active = location;
    }
}
