import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpService} from "../../../../service/http.service";
import {Result} from "../../../../model/result";
import {Host} from "../../../../model/host.model";
import {Attribute} from "../../../../model/attribute.model";

@Component({
    selector: 'app-create-new-attribute-modal',
    templateUrl: './create-new-attribute-modal.component.html',
    styleUrls: ['./create-new-attribute-modal.component.css']
})
export class CreateNewAttributeModalComponent implements OnInit {

    serverError: boolean;
    hosts: Host[];
    processing: boolean;

    availableTypes: { label: string, value: string }[] = [
        {label: 'Text', value: 'TEXT_INPUT'},
        {label: 'Options', value: 'MULTI_CHOICE'},
        {label: 'Boolean', value: 'BOOLEAN'}
    ];
    newAttribute: Attribute;

    constructor(public activeModal: NgbActiveModal, private http: HttpService) {
        this.newAttribute = new Attribute();
    }

    ngOnInit(): void {
        // retrieve available hosts
        this.http.get('hosts').subscribe((result: Result<Host>) => {
            this.hosts = result.requested;
        })
    }

    typeSelectionChange(): void {
    }

    createAttribute(): void {
        // check hosts information that need to be sent to the backend
        this.newAttribute.hosts = [];
        if (this.newAttribute.allOrganisms) {
            this.newAttribute.hosts = this.hosts;
        } else {
            for (let host of this.hosts) {
                if (host.selected)
                    this.newAttribute.hosts.push(host);
            }
        }

        // submit
        this.http.post('attributes', this.newAttribute).subscribe(result => {
            if (!result)
                return; // todo show error message
            this.activeModal.close(result);
        });
    }

    selectAllHosts(e): void {
        for (let host of this.hosts) {
            host.selected = this.newAttribute.allOrganisms;
        }
    }

    checkAllSelected(): void {
        for (let host of this.hosts) {
            this.newAttribute.allOrganisms = host.selected;
            if (!host.selected) {
                this.newAttribute.allOrganisms = false;
                return;
            }
        }
    }
}
