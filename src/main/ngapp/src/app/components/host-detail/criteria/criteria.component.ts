import {Component, Input, OnInit} from '@angular/core';
import {TierCriteria} from "../../../model/tier-criteria.model";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {
    NgbDropdown,
    NgbDropdownItem,
    NgbDropdownMenu,
    NgbDropdownToggle,
    NgbProgressbar
} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-criteria',
    standalone: true,
    templateUrl: './criteria.component.html',
    imports: [
        NgIf,
        NgbProgressbar,
        NgForOf,
        NgClass,
        NgbDropdown,
        NgbDropdownToggle,
        NgbDropdownMenu,
        NgbDropdownItem
    ],
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
