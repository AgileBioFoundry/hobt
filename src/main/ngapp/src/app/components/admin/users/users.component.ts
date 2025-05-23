import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../../service/http.service";
import {Paging} from "../../../model/paging.model";
import {User} from "../../../model/user.model";
import {NgbModal, NgbModalOptions, NgbPagination, NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {AddRoleComponent} from "./add-role/add-role.component";
import {ConfirmActionComponent} from "../../common/confirm-action/confirm-action.component";
import {Role} from "../../../model/role.model";
import {DatePipe, NgClass, NgForOf} from "@angular/common";

@Component({
    selector: 'app-users',
    standalone: true,
    templateUrl: './users.component.html',
    imports: [
        NgForOf,
        NgClass,
        DatePipe,
        NgbTooltip,
        NgbPagination
    ],
    styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

    paging: Paging = new Paging();
    users: User[];

    constructor(private http: HttpService, private modalService: NgbModal) {
    }

    ngOnInit(): void {
        this.pageUsers(1);
    }

    setUserActive(user: User): void {
        user.processing = true;
        // if user is disable (enable)
        if (user.isDisabled) {
            // enable
            this.http.put("users/" + user.id + "/active", {}).subscribe({
                next: (result: any) => {
                    user.processing = false;
                    user.isDisabled = !user.isDisabled;
                }, error: err => {
                    user.processing = false;
                }
            })
        } else {
            this.http.delete("users/" + user.id + "/active").subscribe({
                next: (result: any) => {
                    user.processing = false;
                    user.isDisabled = !user.isDisabled;
                }, error: err => {
                    user.processing = false;
                }
            })
        }
    }

    showAddRoleModal(user: User): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(AddRoleComponent, options);
        modalRef.componentInstance.user = user;
        modalRef.result.then((result) => {
            this.pageUsers(1);
        }, error => {
            console.log('error', error);
        });
    }

    showConfirmationModal(user: User, role: Role): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(ConfirmActionComponent, options);
        modalRef.result.then((result: boolean) => {
            console.log(result);
            if (!result)
                return;

            // delete role
            this.http.delete('users/' + user.id + '/roles/' + role.id).subscribe({
                next: () => {
                    this.pageUsers(1);
                }
            })
        });
    }

    pageUsers(page: number): void {
        this.paging.start = ((page - 1) * this.paging.limit);
        // this.loadingAdminAccountsPage = true;
        this.http.get('users', this.paging).subscribe((result: any) => {
            this.users = result.requested;
            this.paging.available = result.available;
        });
    };

    promptDeleteUser(user: User): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(ConfirmActionComponent, options);
        modalRef.componentInstance.resourceName = 'user';
        modalRef.componentInstance.resourceIdentifier = user.userId;
        modalRef.result.then((result: boolean) => {
            if (!result)
                return;

            // delete confirmed. go ahead
            this.http.delete('users/' + user.id).subscribe({
                next: (result: boolean) => {
                    this.pageUsers(1);
                }
            })
        })
    }
}
