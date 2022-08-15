import {Component, OnInit} from '@angular/core';
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {CreateRoleModalComponent} from "../modal/create-role-modal/create-role-modal.component";
import {HttpService} from "../../../service/http.service";
import {Role} from "../../../model/role.model";
import {Permission} from "../../../model/permission.model";
import {Observable, of} from "rxjs";
import {catchError, debounceTime, distinctUntilChanged, switchMap, tap} from "rxjs/operators";
import {PermissionService} from "../../../service/permission.service";
import {User} from "../../../model/user.model";

@Component({
    selector: 'app-roles',
    templateUrl: './roles.component.html',
    styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {

    roles: Role[];
    newPermission: Permission;
    availableResources: string[];
    searching: boolean;
    searchFailed: boolean;

    constructor(private http: HttpService, private modalService: NgbModal, private permissions: PermissionService) {
        this.availableResources = ['All', this.permissions.TIERS, this.permissions.ORGANISMS];
        this.newPermission = new Permission();
        this.searching = false;
        this.searchFailed = false;
    }

    ngOnInit(): void {
        this.http.get('roles').subscribe((result: Role[]) => {
            this.roles = result;
            for (let role of this.roles) {
                // retrieve role members
                this.http.get('roles/' + role.id + '/members').subscribe((result: User[]) => {
                    role.members = result;
                })
            }
        });
    }

    showCreateRoleModal(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(CreateRoleModalComponent, options);
        modalRef.result.then((result: Role) => {
            if (result)
                this.roles.push(result);
        }, error => {
            console.log('error', error);
        });
    }

    savePermission(role: Role): void {
        // todo : check if permission already exists and update if necessary

        this.http.post('roles/' + role.id + '/permissions', this.newPermission).subscribe((perm: Permission) => {
            if (!role.permissions)
                role.permissions = [];

            role.permissions.push(perm);
            this.newPermission = new Permission();
        });
    }

    searchUsers = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            // tap(() => this.searching = true),
            switchMap(term => term.length < 2 ? of([]) :
                this.http.get('users/autocomplete', {val: term})
                    .pipe(
                        // tap(() => this.searchFailed = false),
                        catchError(() => {
                            this.searchFailed = true;
                            return of([]);
                        }))
            ),
            tap(() => this.searching = false)
        );

    formatter(object: any): string {
        return '';
    }

    selectRoleMember(member, role: Role): void {
        console.log(member);
        if (!role.members)
            role.members = [];
        role.members.push(member.item);
    }
}
