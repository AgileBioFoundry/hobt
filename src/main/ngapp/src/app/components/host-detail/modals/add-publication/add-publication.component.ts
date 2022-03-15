import {Component, Input, OnInit} from '@angular/core';
import {HttpService} from "../../../../service/http.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Publication} from "../../../../model/publication";
import {Host} from "../../../../model/host.model";

@Component({
    selector: 'app-add-permission',
    templateUrl: './add-publication.component.html',
    styleUrls: ['./add-publication.component.css']
})
export class AddPublicationComponent implements OnInit {

    @Input() host: Host;

    isUpdate: false;
    publication: Publication;

    constructor(private http: HttpService, public activeModal: NgbActiveModal) {
        this.publication = new Publication();
    }

    ngOnInit(): void {
    }

    createPublication(): void {
        this.http.post('hosts/' + this.host.id + '/publications', this.publication).subscribe((result: Publication) => {
            this.activeModal.close(result);
        });
    }
}
