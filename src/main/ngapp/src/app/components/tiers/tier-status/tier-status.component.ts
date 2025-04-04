import {Component, Input, OnInit} from '@angular/core';
import {Tier} from "../../../model/tier.model";
import {NgClass, NgIf} from "@angular/common";

@Component({
    selector: 'app-tier-status',
    standalone: true,
    templateUrl: './tier-status.component.html',
    imports: [
        NgIf,
        NgClass
    ],
    styleUrls: ['./tier-status.component.css']
})
export class TierStatusComponent implements OnInit {

    @Input() tier: Tier;

    constructor() {
    }

    ngOnInit(): void {
    }

}
