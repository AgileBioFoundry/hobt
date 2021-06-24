import {Component, OnInit} from '@angular/core';
import {Tier} from "../../../model/tier.model";
import {TierCriteria} from "../../../model/tier-criteria.model";
import {HttpService} from "../../../service/http.service";

@Component({
    selector: 'app-tiers',
    templateUrl: './tiers.component.html',
    styleUrls: ['./tiers.component.css']
})
export class TiersComponent implements OnInit {

    newTier: Tier;
    newCriteria: TierCriteria;
    newTierIndex: number;
    showCreateTier: boolean;
    selectedTier: Tier;
    tiers: Array<Tier>;

    // validation
    newTierLabelInvalid: boolean;

    constructor(private http: HttpService) {
        this.newCriteria = new TierCriteria();
        this.showCreateTier = false;
        this.newTierLabelInvalid = false;
    }

    ngOnInit(): void {
        this.http.get('tiers').subscribe((tiers: Tier[]) => {
            this.tiers = tiers;
        })
    }

    showCreateTierClick(): void {
        this.showCreateTier = true;
        this.newTier = new Tier(this.tiers.length);
        this.newTierIndex = 1;
        this.selectedTier = this.tiers[this.tiers.length - 1];
    }

    addNewTier(): void {

        // validate tier name
        for (const tier of this.tiers) {
            if (tier.label === this.newTier.label) {
                this.newTierLabelInvalid = true;
                return;
            }
        }

        this.http.post('tiers', this.newTier).subscribe((result: Tier) => {
            // on update, for each tier at newTier.index onwards, increment index by one
            for (let i = 0; i < this.tiers.length; i += 1) {
                if (this.tiers[i].index < this.newTier.index)
                    continue;

                this.updateTierIndex(this.tiers[i], this.tiers[i].index + 1);
            }

            this.tiers.push(result);
            this.showCreateTier = false;
        })
    }

    private updateTierIndex(tier: Tier, newIndex: number): void {
        this.http.put('tiers/' + tier.id + '/index/' + newIndex, {}).subscribe((result: Tier) => {
            tier.index = result.index;

            // todo : does this belong here ?
            this.tiers = this.tiers.sort((a, b) => a.index - b.index);
        });
    }

    addNewCriteria(tier: Tier): void {
        if (!this.newCriteria)
            return;

        if (!this.newCriteria.label)
            this.newCriteria.labelInvalid = true;

        if (!this.newCriteria.description)
            this.newCriteria.detailsInvalid = true;

        if (this.newCriteria.detailsInvalid || this.newCriteria.labelInvalid)
            return;

        // add new criteria to server side of application and if successful push to list of criteria
        this.http.post('tiers/' + tier.id + '/criteria', this.newCriteria).subscribe((result: TierCriteria) => {
            if (!tier.criteria)
                tier.criteria = [];

            tier.criteria.push(result);
            this.newCriteria = new TierCriteria();
        })
    }

    tierIndexChange(): void {
        this.newTier.index = this.selectedTier.index + this.newTierIndex;
    }
}
