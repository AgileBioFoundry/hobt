import {Component, Input, OnInit} from '@angular/core';
import {Tier} from "../../../model/tier.model";

@Component({
    selector: 'app-tier-status',
    templateUrl: './tier-status.component.html',
    styleUrls: ['./tier-status.component.css']
})
export class TierStatusComponent implements OnInit {

    @Input() tier: Tier;

    constructor() {
    }

    ngOnInit(): void {
    }

}
