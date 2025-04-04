import {Component, OnInit} from '@angular/core';
import {Tier} from "../../../model/tier.model";
import {TierCriteria} from "../../../model/tier-criteria.model";
import {TierRule} from "../../../model/tier-rules.model";
import {HttpService} from "../../../service/http.service";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {ConfirmActionComponent} from "../../common/confirm-action/confirm-action.component";

@Component({
    selector: 'app-tiers',
    standalone: true,
    templateUrl: './tiers.component.html',
    styleUrls: ['./tiers.component.css']
})
export class TiersComponent implements OnInit {

    newTier: Tier;
    newCriteria: TierCriteria;
    editCriteria: TierCriteria;
    newRule: TierRule;
    newTierIndex: number;

    showCreateTier: boolean;
    selectedTier: Tier;
    tiers: Array<Tier>;

    editIndex: number;

    // validation
    newTierLabelInvalid: boolean;

    constructor(private http: HttpService, private modalService: NgbModal) {
        this.newCriteria = new TierCriteria();
        this.newRule = new TierRule();
        this.showCreateTier = false;
        this.newTierLabelInvalid = false;
    }

    ngOnInit(): void {
        this.http.get('tiers').subscribe((tiers: Tier[]) => {
            this.tiers = tiers;
            for (const tier of this.tiers) {
                tier.criteria = tier.criteria.sort((a, b) => a.id - b.id);

                // retrieve the rules for each tier
                this.http.get('tiers/' + tier.id + '/rules').subscribe((rules: TierRule[]) => {
                    tier.rules = rules;
                })
            }
        });
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

    cancelNewRules(tier: Tier): void {
        tier.showAddRules = false;
        this.newRule = new TierRule();
    }

    addNewRules(tier: Tier): void {
        if (isNaN(this.newRule.percentage)) {
            return;
        }

        // validate
        if (this.newRule.percentage < 0 || this.newRule.percentage > 100) {
            console.error("Invalid rule percentage: " + this.newRule.percentage);
            return;
        }

        this.http.post('tiers/' + tier.id + '/rules', this.newRule).subscribe((rule: TierRule) => {
            tier.rules.push(rule);
            this.newRule = new TierRule();
        })
    }

    cancelNewCriteria(tier: Tier): void {
        tier.showAddCriteria = false;
        this.newCriteria = new TierCriteria();
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

        // check for unique label
        for (const criteria of tier.criteria) {
            if (this.newCriteria.label === criteria.label) {
                this.newCriteria.labelInvalid = true;
                return;
            }
        }

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

    submitCriteriaUpdate(tier: Tier, criteria: TierCriteria): void {
        this.http.put('tiers/' + tier.id + '/criteria/' + this.editCriteria.id, this.editCriteria)
            .subscribe((result: TierCriteria) => {
                if (!result)
                    return;

                if (result.id === criteria.id)
                    criteria = result;
                this.editIndex = undefined;
            })
    }

    updateCriteria(index: number, criteria: TierCriteria): void {
        this.editIndex = index;
        this.editCriteria = criteria;
    }

    deleteCriteria(tier: Tier, criteria: TierCriteria): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(ConfirmActionComponent, options);
        modalRef.componentInstance.resourceName = 'criteria';
        modalRef.componentInstance.resourceIdentifier = criteria.label;
        modalRef.result.then((result: boolean) => {
            if (!result)
                return;

            // process delete of criteria
            console.log(result);
            this.http.delete('tiers/' + tier.id + '/criteria/' + criteria.id).subscribe(result => {
                console.log(result);
            })
        })
    }

    cancelUpdateCriteria(): void {
        this.editCriteria = undefined;
        this.editIndex = -1;
    }
}
