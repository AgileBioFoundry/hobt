import {Component, Input, OnInit} from '@angular/core';
import {TierCriteria} from "../../../model/tier-criteria.model";

@Component({
    selector: 'app-criteria',
    templateUrl: './criteria.component.html',
    styleUrls: ['./criteria.component.css']
})
export class CriteriaComponent implements OnInit {

    @Input() tierCriteriaList: [TierCriteria];

    constructor() {
    }

    ngOnInit(): void {
    }

    setTierOptionAvailability(criteria: TierCriteria): void {
        criteria.available = !criteria.available;
    }
}
