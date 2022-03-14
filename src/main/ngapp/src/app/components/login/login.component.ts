import {Component, OnInit} from '@angular/core';
import {User} from "../../model/user.model";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {HttpService} from "../../service/http.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Permission} from "../../model/permission.model";
import {PermissionService} from "../../service/permission.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    loggedInUser: User;
    canRegister = false;
    processing = false;
    remember: boolean;
    validation: { validId: boolean, validPassword: boolean, invalidPassword: boolean };
    rememberUserKey = 'rememberUser';

    constructor(private http: HttpService, private router: Router, private userService: UserService,
                public activeModal: NgbActiveModal, private permissionService: PermissionService) {
        this.validation = {validId: true, validPassword: true, invalidPassword: false};
    }

    ngOnInit(): void {
        // check if the "remember me" setting is enabled
        this.remember = (localStorage.getItem(this.rememberUserKey) !== null);

        // verify if sessionId is valid when visiting the login page and redirect user to main page if so
        this.loggedInUser = this.userService.getUser();
        if (this.loggedInUser && this.loggedInUser.sessionId) {

            // if user is already logged in, verify that current session id is valid
            this.http.get('accesstokens').subscribe(() => {
                // close modal and send information about logged in user to header
                this.activeModal.close(this.loggedInUser);
            }, error => {
                this.userService.clearUser();
            });
            return;
        }

        if (!this.loggedInUser) {
            this.loggedInUser = new User();
        }
    }

    setRemember(): void {
        this.remember = !this.remember;
        if (this.remember) {
            localStorage.setItem(this.rememberUserKey, 'yes');
        } else {
            localStorage.setItem(this.rememberUserKey, null);
        }
    }

    loginUser(): void {
        this.validation.validId = (this.loggedInUser.userId !== undefined && this.loggedInUser.userId !== '');
        this.validation.validPassword = (this.loggedInUser.password !== undefined && this.loggedInUser.password !== '');

        if (!this.validation.validId || !this.validation.validPassword) {
            return;
        }
        this.validation.invalidPassword = false;

        this.processing = true;
        this.http.post('accesstokens', this.loggedInUser).subscribe((result: User) => {
            this.processing = false;
            if (!result || !result.sessionId)
                return;

            // save to session
            this.loggedInUser = result;
            this.userService.setUser(result);

            // check for explicit permissions only if this user is not an administrator
            if (!this.loggedInUser.isAdmin) {
                this.http.get('users/' + this.loggedInUser.id + '/permissions').subscribe((permissions: Permission[]) => {
                    console.log("setting permissions for " + permissions);
                    this.permissionService.setPermissions(permissions);
                });
            }

            this.activeModal.close(this.loggedInUser);
        }, error => {
            this.processing = false;
            console.error(error);

            if (error.status === 401) {
                this.validation.invalidPassword = true;
            }
        });
    }
}
