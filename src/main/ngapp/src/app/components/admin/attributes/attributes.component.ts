import {Component, OnInit} from '@angular/core';
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {HttpService} from "../../../service/http.service";
import {
    CreateNewAttributeModalComponent
} from "../modal/create-new-attribute-modal/create-new-attribute-modal.component";
import {Paging} from "../../../model/paging.model";
import {Attribute} from "../../../model/attribute.model";
import {Result} from "../../../model/result";

@Component({
    selector: 'app-attributes',
    templateUrl: './attributes.component.html',
    styleUrls: ['./attributes.component.css']
})
export class AttributesComponent implements OnInit {

    paging: Paging;
    attributes: Attribute[];

    constructor(private http: HttpService, private modalService: NgbModal) {
        this.paging = new Paging();
    }

    ngOnInit(): void {
        // todo : retrieve new attributes
        this.pageAttributes();
    }

    pageAttributes(page: number = 1): void {
        this.paging.start = ((page - 1) * this.paging.limit);
        this.paging.processing = true;
        console.log(this.paging);

        this.http.get('attributes', this.paging).subscribe((result: Result<Attribute>) => {
            this.paging.available = result.available;
            this.attributes = result.requested;
            this.paging.processing = false;
        }, error => {
            this.paging.processing = false;
        })
    }

    pageCounts(): string {
        const currentPage = this.paging.currentPage;
        const resultCount = this.paging.available;
        const maxPageCount = this.paging.limit;
        let pageNum = ((currentPage - 1) * maxPageCount) + 1;

        // number on this page
        let pageCount = (currentPage * maxPageCount) > resultCount ? resultCount : (currentPage * maxPageCount);
        return pageNum + " - " + (pageCount) + " of " + (resultCount);
    };


    showCreateAttributeModal(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false, size: 'lg'};
        const modalRef = this.modalService.open(CreateNewAttributeModalComponent, options);
        modalRef.result.then((result: Attribute) => {
            if (result) {
                this.pageAttributes();
            }
        }, error => {
            console.log('error', error);
        });
    }

}
