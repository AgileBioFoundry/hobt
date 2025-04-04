import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {AddAttributeComponent} from "../modals/add-attribute/add-attribute.component";
import {Paging} from "../../../model/paging.model";
import {HttpService} from "../../../service/http.service";
import {Attribute} from "../../../model/attribute.model";
import {User} from "../../../model/user.model";
import {UserService} from "../../../service/user.service";
import {ConfirmActionComponent} from "../../common/confirm-action/confirm-action.component";
import {NgForOf, NgIf} from "@angular/common";

@Component({
    selector: 'app-attributes',
    standalone: true,
    templateUrl: './attributes.component.html',
    imports: [
        NgIf,
        NgForOf
    ],
    styleUrls: ['./attributes.component.css']
})
export class AttributesComponent implements OnInit {

    @Input() host: Host;
    @Input() canEdit: boolean;

    attributes: Attribute[];
    paging: Paging;
    user: User;

    constructor(private modalService: NgbModal, private http: HttpService, private userService: UserService) {
        this.paging = new Paging();
        this.user = this.userService.getUser();
    }

    ngOnInit(): void {
        this.getAttributeList();
    }

    private getAttributeList(): void {
        this.http.get('hosts/' + this.host.id + '/attributes/values').subscribe((result: Attribute[]) => {
            this.attributes = result;
        });
    }

    deleteAttribute(attribute: Attribute): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false, size: 'md'};
        const modalRef = this.modalService.open(ConfirmActionComponent, options);
        modalRef.componentInstance.resourceName = 'Attribute';
        modalRef.componentInstance.resourceIdentifier = attribute.label;
        modalRef.result.then((result: boolean) => {
            if (!result)
                return;

            // delete attribute
            this.http.delete('hosts/' + this.host.id + '/attributes/' + attribute.id).subscribe({
                next: (result: boolean) => {
                    if (!result)
                        return;

                    this.getAttributeList();
                }
            })
        })

    }

    showAttributeAddModal(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false, size: 'lg'};
        const modalRef = this.modalService.open(AddAttributeComponent, options);
        modalRef.componentInstance.hostId = this.host.id;

        modalRef.result.then((result) => {
            console.log(result);
        }, error => {
            console.log('error', error);
        });
    }
}
