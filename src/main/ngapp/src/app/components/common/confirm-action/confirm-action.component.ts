import {Component, Input} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {NgIf} from "@angular/common";

@Component({
    selector: 'app-confirm-action',
    templateUrl: './confirm-action.component.html',
    standalone: true,
    imports: [
        NgIf
    ],
    styleUrls: ['./confirm-action.component.css']
})
export class ConfirmActionComponent {

    @Input() resourceName: string;
    @Input() resourceIdentifier: string;

    constructor(public activeModal: NgbActiveModal) {
    }

    deleteConfirmed(): void {
        this.activeModal.close(true);
    }
}
