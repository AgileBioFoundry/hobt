import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpService} from "../../../../service/http.service";
import {Attribute} from "../../../../model/attribute.model";

@Component({
    selector: 'app-add-attribute',
    templateUrl: './add-attribute.component.html',
    styleUrls: ['./add-attribute.component.css']
})
export class AddAttributeComponent implements OnInit {

    @Input() hostId: number;
    attributes: Attribute[];

    constructor(public activeModal: NgbActiveModal, private http: HttpService) {
    }

    ngOnInit(): void {
        this.http.get('attributes?organism=' + this.hostId).subscribe((result: Attribute[]) => {
            this.attributes = result;
        });
    }

    saveAttribute(): void {
        this.http.put('hosts/' + this.hostId + '/attributes/values', {id: this.hostId, attributes: this.attributes})
            .subscribe(result => {

            })
    }
}
