import {Component, OnInit} from '@angular/core';
import {Tier} from "../../../model/tier.model";
import {TierCriteria} from "../../../model/tier-criteria.model";
import {HttpService} from "../../../service/http.service";

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

    newTier: Tier;
    newCriteria: TierCriteria;
    showCreateTier: boolean;
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
    }

    addNewTier(): void {

        // validate tier name
        for (const tier of this.tiers) {
            if (tier.label === this.newTier.label) {
                this.newTierLabelInvalid = true;
                return;
            }
        }

        this.newTier.index = this.tiers.length;
        this.http.post('tiers', this.newTier).subscribe((result: Tier) => {
            this.tiers.push(result);
            this.showCreateTier = false;
        })
    }

    addNewCriteria(tier: Tier): void {
        if (!this.newCriteria)
            return;

        if (!this.newCriteria.label)
            this.newCriteria.labelInvalid = true;

        if (!this.newCriteria.details)
            this.newCriteria.detailsInvalid = true;

        if (this.newCriteria.detailsInvalid || this.newCriteria.labelInvalid)
            return;

        if (!tier.criteria)
            tier.criteria = [];

        tier.criteria.push(this.newCriteria);
        this.newCriteria = new TierCriteria();
    }
}
