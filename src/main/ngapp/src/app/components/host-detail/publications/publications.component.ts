import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {HttpService} from "../../../service/http.service";
import {NgbModal, NgbModalOptions, NgbPagination} from "@ng-bootstrap/ng-bootstrap";
import {AddPublicationComponent} from "../modals/add-publication/add-publication.component";
import {Result} from "../../../model/result";
import {Paging} from "../../../model/paging.model";
import {NgIf} from "@angular/common";
import {Publication} from "../../../model/Publication";

@Component({
    selector: 'app-publications',
    standalone: true,
    templateUrl: './publications.component.html',
    imports: [
        NgIf,
        NgbPagination
    ],
    styleUrls: ['./publications.component.css']
})
export class PublicationsComponent implements OnInit {

    @Input() host: Host;
    @Input() canEdit: boolean;

    publications: Publication[];
    paging: Paging;

    constructor(private http: HttpService, private modalService: NgbModal) {
        this.paging = new Paging();
    }

    ngOnInit(): void {
        this.pagePublications();
    }

    pagePublications(page = 1): void {
        this.paging.start = ((page - 1) * this.paging.limit);
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
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false, size: 'lg'};
        const modalRef = this.modalService.open(AddPublicationComponent, options);
        modalRef.componentInstance.host = this.host;
        modalRef.result.then((result: any) => {
            if (!result || result === 'cancel')
                return;

            this.pagePublications();
        }, error => {
            console.log('error', error);
        });
    }
}
