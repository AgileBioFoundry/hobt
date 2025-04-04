import {Component} from '@angular/core';
import {ActivatedRoute, Router, RouterOutlet} from "@angular/router";
import {NgClass} from "@angular/common";
import {HeaderComponent} from "../header/header.component";

@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    standalone: true,
    imports: [
        NgClass,
        RouterOutlet,
        HeaderComponent
    ],
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
