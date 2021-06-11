import {Component, OnInit} from '@angular/core';
import {User} from "../../model/user.model";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {HttpService} from "../../service/http.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

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
                public activeModal: NgbActiveModal) {
        this.validation = {validId: true, validPassword: true, invalidPassword: false};
    }

    ngOnInit(): void {
        // check if the "remember me" setting is enabled
        this.remember = (localStorage.getItem(this.rememberUserKey) !== null);

        // verify if sessionId is valid when visiting the login page and
        // redirect user to main page if so
        this.loggedInUser = this.userService.getUser();
        if (this.loggedInUser && this.loggedInUser.sessionId) {
            this.http.get('accesstoken').subscribe(() => {
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
            console.log('set local storage');
            console.log(localStorage.getItem(this.rememberUserKey));
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
            console.log(result);
            this.processing = false;

            // check if password needs to be created and re-direct if so
            // if (result.usingTemporaryPassword) {
            //     this.userService.setUser(result);
            //     this.router.navigate(['/resetPassword']);
            //     return;
            // }

            // save to session
            this.loggedInUser = result;
            if (result && result.sessionId) {
                this.userService.setUser(result);

                // redirect
                // let redirectUrl = this.userService.getLoginRedirect();
                // if (redirectUrl === '/register' || redirectUrl === '/forgotPassword' || redirectUrl === '/login' || !redirectUrl) {
                //     redirectUrl = '/';
                // }
                // this.router.navigate([redirectUrl]);
                this.activeModal.close(this.loggedInUser);
            }
        }, error => {
            this.processing = false;
            console.error(error);

            if (error.status === 401) {
                this.validation.invalidPassword = true;
            }
        });
    }
}
