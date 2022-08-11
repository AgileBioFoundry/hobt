import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {HttpService} from "../../../service/http.service";
import {Tier} from "../../../model/tier.model";
import {TierCriteria} from "../../../model/tier-criteria.model";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/user.model";
import {OrganismCriteria} from "../../../model/organism-criteria.model";
import {TierStatus} from "../../../model/tier-status";

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
        this.user = this.users.getUser();
        this.canEditTiers = this.checkCanEditTiers();
    }

    ngOnInit(): void {
        this.http.get('tiers').subscribe((tiers: Tier[]) => {
            this.tiers = tiers;
            console.log(tiers);

            // todo : this whole section is very inefficient
            this.http.get('hosts/' + this.host.id + '/tiers/status').subscribe((result: TierStatus[]) => {
                console.log(result);
                for (const tier of this.tiers) {
                    for (const status of result) {
                        if (tier.id === status.tierId) {
                            tier.completed = status.complete;
                            tier.collapsed = tier.completed;
                        }
                    }
                }
            });

            this.http.get('hosts/' + this.host.id + '/criterias').subscribe((result: OrganismCriteria[]) => {
                if (!result.length)
                    return;

                // for each tier in list of tiers for this organism
                for (const tier of this.tiers) {

                    // for each criterion in each tier
                    for (const criteria of tier.criteria) {

                        // go through returned criteria and set
                        criteria.status = this.getTierCriteriaStatus(criteria.id, result);
                    }

                    tier.criteria = tier.criteria.sort((a, b) => a.id - b.id);
                    // tier.collapsed = (tier.index <= this.host.tier.index);
                }

                // todo : use criteriaId -> [criteria]
            })
        })
    }

    getTierCriteriaStatus(criteriaId: number, organismCriteria: OrganismCriteria[]): number {
        for (const item of organismCriteria) {
            if (item.criteria.id === criteriaId) {
                return item.percentageComplete;
            }
        }
        return 0;
    }

    sortBy(criteriaArray: Array<TierCriteria>, prop: string) {
        return criteriaArray.sort((a, b) => a[prop] > b[prop] ? 1 : a[prop] === b[prop] ? 0 : -1);
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
        const tierStatus: TierStatus = {hostId: this.host.id, tierId: tier.id, complete: !tier.completed}
        this.http.put('hosts/' + this.host.id + '/tiers/status', tierStatus).subscribe(result => {

        });

        tier.completed = !tier.completed;
        tier.collapsed = tier.completed;
    }

    setTierCriteriaStatus(tier: Tier, criteria: TierCriteria, status: number): void {
        this.http.post('hosts/' + this.host.id + '/criterias/' + criteria.id + '/status', {
            id: criteria.id,
            status: status
        }).subscribe((result) => {
            criteria.status = status;
        });
    }
}
