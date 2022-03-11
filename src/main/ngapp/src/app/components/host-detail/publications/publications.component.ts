import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {HttpService} from "../../../service/http.service";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {AddPublicationComponent} from "../modals/add-publication/add-publication.component";
import {Publication} from "../../../model/publication";
import {Result} from "../../../model/result";
import {Paging} from "../../../model/paging.model";

@Component({
    selector: 'app-publications',
    templateUrl: './publications.component.html',
    styleUrls: ['./publications.component.css']
})
export class PublicationsComponent implements OnInit {

    @Input() host: Host;
    publications: Publication[];
    paging: Paging;

    constructor(private http: HttpService, private modalService: NgbModal) {
        this.paging = new Paging();
    }

    ngOnInit(): void {
        this.paging.processing = true;
        this.http.get('hosts/' + this.host.id + '/publications', this.paging).subscribe((result: Result<Publication>) => {
            this.publications = result.requested
            this.paging.available = result.available;
            this.paging.processing = false;
        }, error => {
            this.paging.processing = false;
        })
    }

    showPublicationAddModal(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(AddPublicationComponent, options);
        modalRef.componentInstance.host = this.host;
        modalRef.result.then((result: any) => {
            if (!result || result === 'cancel')
                return;

            console.log(result);
            this.publications.push(result);
        }, error => {
            console.log('error', error);
        });
    }
}
