import {Component, OnInit} from '@angular/core';
import {LoginComponent} from "../login/login.component";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";
import {RegisterComponent} from "../register/register.component";
import {HttpService} from "../../service/http.service";
import {NgIf} from "@angular/common";

@Component({
    selector: 'app-header',
    standalone: true,
    templateUrl: './header.component.html',
    imports: [
        NgIf
    ],
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    loggedInUser: User;

    constructor(private modalService: NgbModal, private router: Router, private userService: UserService,
                private http: HttpService) {
    }

    ngOnInit(): void {
        this.loggedInUser = this.userService.getUser();
        if (this.loggedInUser) {
            // verify
            this.http.get('accesstokens').subscribe(() => {
            }, error => {
                // invalid session id so log user out
                this.userService.clearUser();
                this.loggedInUser = undefined;
            })
        }
    }

    loginUser(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(LoginComponent, options);
        modalRef.result.then((loggedInUser: User) => {
            this.loggedInUser = loggedInUser;
            window.location.reload();
            // console.log('reloading', this.router.url);
            // this.router.navigate([this.router.url])
        }, () => {
            // console.log('delete modal cancel');
        });
    }

    registerUser(): void {
        const options: NgbModalOptions = {backdrop: 'static', size: 'md', keyboard: false};
        const modalRef = this.modalService.open(RegisterComponent, options);
        modalRef.result.then(() => {

        });
    }

    logoutUser(): void {
        this.userService.clearUser();
        this.loggedInUser = undefined;
        window.location.reload();
    }

    goHome(): void {
        this.router.navigate(['/']);
    }

    goToAdminSettings(): void {
        this.router.navigate(['/admin']);
    }
}
