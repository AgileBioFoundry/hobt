import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {HttpService} from "../../../service/http.service";
import {Tier} from "../../../model/tier.model";
import {TierCriteria} from "../../../model/tier-criteria.model";

@Component({
    selector: 'app-host-tiers',
    templateUrl: './host-tiers.component.html',
    styleUrls: ['./host-tiers.component.css']
})
export class HostTiersComponent implements OnInit {

    @Input() host: Host;
    tiers: Tier[];

    constructor(private http: HttpService) {
    }

    ngOnInit(): void {
        this.http.get('tiers').subscribe((result: Tier[]) => {
            console.log(result);
            this.tiers = result;
        })
    }

    setTierCriteriaStatus(tier: Tier, criteria: TierCriteria, status: number): void {
        criteria.status = status;
    }
}
