import {Component, OnInit} from '@angular/core';
import {Role} from "../../../../model/role.model";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpService} from "../../../../service/http.service";

@Component({
    selector: 'app-create-role-modal',
    templateUrl: './create-role-modal.component.html',
    styleUrls: ['./create-role-modal.component.css']
})
export class CreateRoleModalComponent implements OnInit {

    newRole: Role;
    existingLabel: boolean;

    constructor(public activeModal: NgbActiveModal, private http: HttpService) {
        this.newRole = new Role();
    }

    ngOnInit(): void {
    }

    createNewRole(): void {
        this.http.get('roles/' + this.newRole.label).subscribe((existing: Role) => {
            if (existing)
                this.existingLabel = true;
        }, error => {

        });


    }
}
