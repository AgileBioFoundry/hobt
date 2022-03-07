import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {HttpService} from "../../../service/http.service";
import {Tier} from "../../../model/tier.model";
import {TierCriteria} from "../../../model/tier-criteria.model";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/user.model";

@Component({
    selector: 'app-host-tiers',
    templateUrl: './host-tiers.component.html',
    styleUrls: ['./host-tiers.component.css']
})
export class HostTiersComponent implements OnInit {

    @Input() host: Host;
    tiers: Tier[];
    user: User;
    canEditTiers: boolean;

    constructor(private http: HttpService, private users: UserService) {
        this.user = this.users.getUser(false);
        this.canEditTiers = this.checkCanEditTiers();
    }

    ngOnInit(): void {
        this.http.get('tiers').subscribe((tiers: Tier[]) => {
            this.tiers = tiers;

            this.http.get('hosts/' + this.host.id + '/criterias/').subscribe((result: TierCriteria[]) => {
                console.log(result);

                for (const tier of this.tiers) {
                    for (const criteria of tier.criteria) {
                        criteria.status = this.getTierCriteriaStatus(criteria.id, result);
                    }
                    tier.criteria = tier.criteria.sort((a, b) => a.id - b.id);
                    tier.collapsed = (tier.index <= this.host.tier.index);
                }

                // todo : use criteriaId -> [criteria]
            })
        })
    }

    //
    // permissions
    //
    checkCanEditTiers(): boolean {
        return this.user != undefined;   // todo : and has roles
    }

    // override tier completion
    markTierCompleted(tier: Tier): void {
        if (!this.canEditTiers)
            return;

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
