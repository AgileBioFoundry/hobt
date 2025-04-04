import {Component} from '@angular/core';
import {Role} from "../../../../model/role.model";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpService} from "../../../../service/http.service";
import {FormsModule} from "@angular/forms";
import {NgClass} from "@angular/common";

@Component({
    selector: 'app-create-role-modal',
    standalone: true,
    templateUrl: './create-role-modal.component.html',
    imports: [
        FormsModule,
        NgClass
    ],
    styleUrls: ['./create-role-modal.component.css']
})
export class CreateRoleModalComponent {

    newRole: Role;
    roles: Role[];
    existingLabel: boolean;
    serverError: boolean;

    constructor(public activeModal: NgbActiveModal, private http: HttpService) {
        this.newRole = new Role();
        this.serverError = false;
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
