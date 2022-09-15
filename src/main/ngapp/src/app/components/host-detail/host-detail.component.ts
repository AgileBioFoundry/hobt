import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Host} from "../../model/host.model";
import {Location} from "@angular/common";

@Component({
    selector: 'app-host-detail',
    templateUrl: './host-detail.component.html',
    styleUrls: ['./host-detail.component.css']
})
export class HostDetailComponent implements OnInit {

    host: Host;
    active: 'attributes';

    constructor(private route: ActivatedRoute, private location: Location) {
    }

    ngOnInit(): void {
        this.route.data.subscribe((data) => {
            this.host = data.host;
            if (!this.host) {
                this.host = new Host();
            }
        });

        const param = this.route.snapshot.params['attribute'];
        if (param)
            this.active = param;
    }

    activeChanged(event): void {
        this.location.go('/host/' + this.host.id + '/' + event.nextId);
    }

    tiers = [];
}
