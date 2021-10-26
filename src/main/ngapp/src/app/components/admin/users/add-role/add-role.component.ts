import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Role} from "../../../../model/role.model";
import {HttpService} from "../../../../service/http.service";
import {User} from "../../../../model/user.model";

@Component({
    selector: 'app-add-role',
    templateUrl: './add-role.component.html',
    styleUrls: ['./add-role.component.css']
})
export class AddRoleComponent implements OnInit {

    @Input() user: User;
    roles: Role[];
    serverError: boolean;
    selectedRole: Role;

    constructor(public activeModal: NgbActiveModal, private http: HttpService) {
        this.http.get('roles').subscribe((result: Role[]) => {
            this.roles = result;
        });
    }

    ngOnInit(): void {
    }

    createNewRole(): void {
        this.http.put('users/' + this.user.id + '/roles', this.selectedRole).subscribe(result => {
            this.activeModal.close(this.selectedRole);
        });
    }
}
