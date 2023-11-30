import {Component} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {User} from "../../model/user.model";
import {HttpService} from "../../service/http.service";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent {

    newUser: User;
    processing: boolean;
    submitted: boolean;
    serverError: boolean;
    validationError: boolean;
    accountCreated: boolean;

    constructor(private http: HttpService, public activeModal: NgbActiveModal) {
        this.newUser = new User();
    }

    registerNewUser(): void {
        this.submitted = false;
        this.serverError = false;

        if (!this.userInformationValid())
            return;

        this.http.post('users', this.newUser).subscribe({
            next: (result: any) => {
                console.log(result);
                this.accountCreated = true;
            }, error: err => {
                console.error(err); // returns 500 even in cases where user id is being duplicated
                this.serverError = true;
            }
        });
    }

    userInformationValid(): boolean {
        this.newUser.firstNameValid = this.newUser.firstName !== undefined;
        this.newUser.lastNameValid = this.newUser.lastName !== undefined;
        this.newUser.userIdValid = this.newUser.email !== undefined;
        this.newUser.institutionValid = this.newUser.institution !== undefined;
        this.newUser.descriptionValid = this.newUser.description !== undefined;

        return this.newUser.firstNameValid && this.newUser.lastNameValid && this.newUser.userIdValid
            && this.newUser.institutionValid && this.newUser.descriptionValid;
    }
}
