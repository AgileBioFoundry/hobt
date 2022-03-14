import {Component, OnInit} from '@angular/core';
import {LoginComponent} from "../login/login.component";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";
import {RegisterComponent} from "../register/register.component";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    loggedInUser: User;

    constructor(private modalService: NgbModal, private router: Router, private userService: UserService) {
        this.loggedInUser = this.userService.getUser();
    }

    ngOnInit(): void {
    }

    loginUser(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(LoginComponent, options);
        modalRef.result.then((loggedInUser: User) => {
            this.loggedInUser = loggedInUser;
            console.log(loggedInUser);
        }, () => {
            // console.log('delete modal cancel');
        });
    }

    registerUser(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(RegisterComponent, options);
        modalRef.result.then(() => {

        });
    }

    logoutUser(): void {
        this.userService.clearUser();
        this.loggedInUser = undefined;
    }

    goHome(): void {
        this.router.navigate(['/']);
    }
}
