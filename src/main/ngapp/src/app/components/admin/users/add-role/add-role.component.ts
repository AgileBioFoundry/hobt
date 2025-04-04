import {Component, Input} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Role} from "../../../../model/role.model";
import {HttpService} from "../../../../service/http.service";
import {User} from "../../../../model/user.model";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-add-role',
    standalone: true,
    templateUrl: './add-role.component.html',
    imports: [
        NgClass,
        FormsModule,
        NgForOf,
        NgIf
    ],
    styleUrls: ['./add-role.component.css']
})
export class AddRoleComponent {

    @Input() user: User;
    roles: Role[];
    serverError: boolean;
    selectedRole: Role;

    constructor(public activeModal: NgbActiveModal, private http: HttpService) {
        this.http.get('roles').subscribe((result: Role[]) => {
            this.roles = result;
        });
    }

    createNewRole(): void {
        this.http.put('users/' + this.user.id + '/roles', this.selectedRole).subscribe(result => {
            this.activeModal.close(this.selectedRole);
        });
    }
}
