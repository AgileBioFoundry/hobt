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
        this.http.get('tiers').subscribe((tiers: Tier[]) => {
            this.tiers = tiers;

            this.http.get('hosts/' + this.host.id + '/criterias/').subscribe((result: TierCriteria[]) => {
                for (const tier of this.tiers) {
                    for (const criteria of tier.criteria) {
                        criteria.status = this.getTierCriteriaStatus(criteria.id, result);
                    }
                    tier.criteria = tier.criteria.sort((a, b) => a.id - b.id);
                }

                // todo : use criteriaId -> [criteria]
            })
        })
    }

    markTierCompleted(tier: Tier): void {
        // todo : make call to backend
        tier.completed = true;
        tier.collapsed = true;
    }

    setTierCriteriaStatus(tier: Tier, criteria: TierCriteria, status: number): void {
        this.http.post('hosts/' + this.host.id + '/criterias/' + criteria.id + '/status', {
            id: criteria.id,
            status: status
        }).subscribe((result) => {
            criteria.status = status;
        });
    }

    getTierCriteriaStatus(criteriaId: number, tierCriterias: TierCriteria[]): number {
        for (const criteria of tierCriterias) {
            if (criteria.id === criteriaId)
                return criteria.status;
        }
        return 0;
    }
}
