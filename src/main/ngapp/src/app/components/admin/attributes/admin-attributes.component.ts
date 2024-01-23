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
    selector: 'app-admin-attributes',
    templateUrl: './admin-attributes.component.html',
    styleUrls: ['./admin-attributes.component.css']
})
export class AdminAttributesComponent implements OnInit {

    paging: Paging;
    attributes: Attribute[];
    availableTypes: { label: string, value: string }[] = [
        {label: 'Text', value: 'TEXT_INPUT'},
        {label: 'Multi Choice Options', value: 'MULTI_CHOICE'},
        {label: 'Boolean', value: 'BOOLEAN'}
    ];

    constructor(private http: HttpService, private modalService: NgbModal) {
        this.paging = new Paging();
    }

    ngOnInit(): void {
        // todo : retrieve new attributes
        this.pageAttributes();
    }

    attributeTypeLabel(type: string): string {
        for (let i = 0; i < this.availableTypes.length; i += 1) {
            const typeC = this.availableTypes[i];
            if (typeC.value === type)
                return typeC.label;
        }
        return type;
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

    showCreateAttributeModal(attribute?: Attribute): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false, size: 'lg'};
        const modalRef = this.modalService.open(CreateNewAttributeModalComponent, options);
        if (!attribute)
            attribute = new Attribute();
        modalRef.componentInstance.newAttribute = attribute;

        modalRef.result.then((result: Attribute) => {
            if (result) {
                this.pageAttributes();
            }
        }, error => {
            console.log('error', error);
        });
    }
}
