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
    roles: Role[];
    existingLabel: boolean;
    serverError: boolean;

    constructor(public activeModal: NgbActiveModal, private http: HttpService) {
        this.newRole = new Role();
        this.serverError = false;
    }

    ngOnInit(): void {
    }

    createNewRole(): void {
        this.serverError = false;
        this.http.post('roles', this.newRole).subscribe((created: Role) => {
            this.activeModal.close(created);
        }, error => {
            this.serverError = true;
        });
    }
}
