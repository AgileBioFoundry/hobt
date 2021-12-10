import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../../service/http.service";
import {Paging} from "../../../model/paging.model";
import {User} from "../../../model/user.model";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {AddRoleComponent} from "./add-role/add-role.component";
import {ConfirmActionComponent} from "../../common/confirm-action/confirm-action.component";

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

    paging: Paging;
    users: User[];

    constructor(private http: HttpService, private modalService: NgbModal) {
        this.paging = new Paging();
    }

    ngOnInit(): void {
        this.pageUsers(1);
    }

    showAddRoleModal(user: User): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(AddRoleComponent, options);
        modalRef.componentInstance.user = user;
    }

    showConfirmationModal(user: User): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(ConfirmActionComponent, options);
    }

    // fetchUsers(): void {
    //     this.http.get('users', this.paging).subscribe((result: any) => {
    //         console.log(result);
    //         this.users = result.requested;
    //         this.paging.available = result.available;
    //     }, error => {
    //         console.log(error);
    //     });
    // }

    pageUsers(page: number): void {
        this.paging.start = ((page - 1) * this.paging.limit);
        // this.loadingAdminAccountsPage = true;
        this.http.get('users', this.paging).subscribe((result: any) => {
            this.users = result.requested;
            this.paging.available = result.available;
        });
    };
}
