import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Host} from "../../model/host.model";
import {Location} from "@angular/common";
import {HostStatistics} from "../../model/host-statistics";
import {HttpService} from "../../service/http.service";
import {PermissionService} from "../../service/permission.service";
import {Tier} from "../../model/tier.model";
import {UserService} from "../../service/user.service";

@Component({
    selector: 'app-host-detail',
    templateUrl: './host-detail.component.html',
    styleUrls: ['./host-detail.component.css']
})
export class HostDetailComponent implements OnInit {

    host: Host;
    active: 'attributes';
    canWrite: boolean;
    userLoggedIn: boolean;

    constructor(private route: ActivatedRoute, private http: HttpService, private user: UserService,
                private location: Location, private permission: PermissionService) {
    }

    ngOnInit(): void {
        this.route.data.subscribe((data) => {
            this.host = data.host;
            if (!this.host) {
                this.host = new Host();
            } else {
                this.http.get('hosts/' + this.host.id + '/statistics').subscribe((result: HostStatistics) => {
                    this.host.statistics = result;
                    this.canWrite = this.permission.canWrite('hosts', this.host.id.toString());
                });
            }
        });

        // check if user is logged in
        this.userLoggedIn = (this.user.getUser() != undefined);

        const param = this.route.snapshot.params['attribute'];
        if (param)
            this.active = param;
    }

    activeChanged(event): void {
        this.location.go('/host/' + this.host.id + '/' + event.nextId);
    }

    getProgressType(tier: Tier): string {
        if (!tier)
            return 'secondary';

        switch (tier.index) {
            case 0:
            default:
                return 'secondary';
            case 1:
                return 'primary';
            case 2:
                return 'info';
            case 3:
                return 'success';
        }
    }

    tiers = [];
}
