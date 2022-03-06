import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../../../service/http.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Publication} from "../../../../model/publication";

@Component({
    selector: 'app-add-permission',
    templateUrl: './add-publication.component.html',
    styleUrls: ['./add-publication.component.css']
})
export class AddPublicationComponent implements OnInit {

    isUpdate: false;
    publication: Publication;

    constructor(private http: HttpService, public activeModal: NgbActiveModal) {
        this.publication = new Publication();
    }

    ngOnInit(): void {
    }

    createPublication(): void {
        this.activeModal.close(this.publication);
    }
}
