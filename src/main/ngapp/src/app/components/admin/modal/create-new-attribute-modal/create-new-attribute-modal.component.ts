import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal, NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {HttpService} from "../../../../service/http.service";
import {Result} from "../../../../model/result";
import {Host} from "../../../../model/host.model";
import {Attribute} from "../../../../model/attribute.model";
import {AttributeOption} from "../../../../model/attribute-option.model";
import {FormsModule} from "@angular/forms";
import {NgClass, NgForOf, NgIf} from "@angular/common";

@Component({
    selector: 'app-create-new-attribute-modal',
    standalone: true,
    templateUrl: './create-new-attribute-modal.component.html',
    imports: [
        FormsModule,
        NgForOf,
        NgbTooltip,
        NgClass,
        NgIf
    ],
    styleUrls: ['./create-new-attribute-modal.component.css']
})
export class CreateNewAttributeModalComponent implements OnInit {

    serverError: boolean;
    hosts: Host[];
    processing: boolean;

    availableTypes: { label: string, value: string }[] = [
        {label: 'Text', value: 'TEXT_INPUT'},
        {label: 'Multi Choice Options', value: 'MULTI_CHOICE'},
        {label: 'Boolean', value: 'BOOLEAN'}
    ];
    @Input() newAttribute: Attribute;

    constructor(public activeModal: NgbActiveModal, private http: HttpService) {
    }

    ngOnInit(): void {
        // retrieve available hosts
        this.http.get('hosts').subscribe((result: Result<Host>) => {
            this.hosts = result.requested;
        })
    }

    typeSelectionChange(): void {
        console.log(this.newAttribute);
        if (this.newAttribute.type === 'MULTI_CHOICE') {
            // check for attribute options list
            if (!this.newAttribute.options) {
                this.newAttribute.options = [new AttributeOption()];
            }
        }
    }

    addAttributeOption(index: number): void {
        this.newAttribute.options.splice(index + 1, 0, new AttributeOption());
    }

    removeAttributeOption(index: number): void {
        this.newAttribute.options.splice(index, 1);
    }

    createOrUpdateAttribute(): void {
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

        // check options
        if (this.newAttribute.type !== 'MULTI_CHOICE') {
            this.newAttribute.options = [];
        }

        // submit
        if (this.newAttribute.id) {
            this.http.put('attributes/' + this.newAttribute.id, this.newAttribute).subscribe((result: Attribute) => {
                this.activeModal.close(result);
            })
        } else {
            this.http.post('attributes', this.newAttribute).subscribe(result => {
                if (!result)
                    return; // todo show error message
                this.activeModal.close(result);
            });
        }
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
