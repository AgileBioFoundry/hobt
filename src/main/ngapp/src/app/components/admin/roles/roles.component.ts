import {Component, OnInit} from '@angular/core';
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {Publication} from "../../../model/Publication";
import {CreateRoleModalComponent} from "../modal/create-role-modal/create-role-modal.component";
import {HttpService} from "../../../service/http.service";
import {Role} from "../../../model/role.model";

@Component({
    selector: 'app-roles',
    templateUrl: './roles.component.html',
    styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {

    roles: Role[];

    constructor(private http: HttpService, private modalService: NgbModal) {
    }

    ngOnInit(): void {
        this.http.get('roles').subscribe((result: Role[]) => {
            this.roles = result;
        });
    }

    showCreateRoleModal(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(CreateRoleModalComponent, options);
        modalRef.result.then((result: Publication) => {
            console.log(result);
            // if (result)
            // this.publications.push(result);
        }, error => {
            console.log('error', error);
        });
    }
}
