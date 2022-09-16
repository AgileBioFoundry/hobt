import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {AddAttributeComponent} from "../modals/add-attribute/add-attribute.component";
import {Paging} from "../../../model/paging.model";
import {HttpService} from "../../../service/http.service";
import {Attribute} from "../../../model/attribute.model";

@Component({
    selector: 'app-attributes',
    templateUrl: './attributes.component.html',
    styleUrls: ['./attributes.component.css']
})
export class AttributesComponent implements OnInit {

    @Input() host: Host;
    @Input() canEdit: boolean;

    attributes: Attribute[];
    paging: Paging;

    constructor(private modalService: NgbModal, private http: HttpService) {
        this.paging = new Paging();
    }

    ngOnInit(): void {
        this.http.get('hosts/' + this.host.id + '/attributes/values').subscribe((result: Attribute[]) => {
            this.attributes = result;
        });
    }

    showAttributeAddModal(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false, size: 'lg'};
        const modalRef = this.modalService.open(AddAttributeComponent, options);
        console.log(this.host);
        modalRef.componentInstance.hostId = this.host.id;

        modalRef.result.then((result) => {
            console.log(result);
        }, error => {
            console.log('error', error);
        });
    }
}
