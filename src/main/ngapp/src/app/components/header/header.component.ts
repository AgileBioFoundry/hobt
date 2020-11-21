import {Component, OnInit} from '@angular/core';
import {LoginComponent} from "../login/login.component";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

    constructor(private modalService: NgbModal, private router: Router) {
    }

    ngOnInit(): void {
    }

    loginUser(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(LoginComponent, options);
        // modalRef.componentInstance.design = Object.assign({}, this.design);
        modalRef.result.then(() => {
            // this.router.navigate(['/designs']);
        }, () => {
            // console.log('delete modal cancel');
        });
    }
}
