import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-add-attribute',
    templateUrl: './add-attribute.component.html',
    styleUrls: ['./add-attribute.component.css']
})
export class AddAttributeComponent implements OnInit {

    constructor(public activeModal: NgbActiveModal) {
    }

    ngOnInit(): void {
    }

    createAttribute(): void {
        
    }
}
