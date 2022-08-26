import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css']
})
export class AdminComponent  {

    active: string;

    constructor(private route: ActivatedRoute) {
        // route.url.subscribe(() => {
            this.active = (route.snapshot.firstChild.routeConfig.path);
        // })
    }
}
