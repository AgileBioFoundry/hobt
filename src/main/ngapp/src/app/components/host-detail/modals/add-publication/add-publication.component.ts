import {Component, Input} from '@angular/core';
import {HttpService} from "../../../../service/http.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Host} from "../../../../model/host.model";
import {Publication} from "../../../../model/Publication";
import {NgClass, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-add-permission',
    standalone: true,
    templateUrl: './add-publication.component.html',
    imports: [
        NgClass,
        FormsModule,
        NgIf
    ],
    styleUrls: ['./add-publication.component.css']
})
export class AddPublicationComponent {

    @Input() host: Host;

    isUpdate: false;
    publication: Publication;

    constructor(private http: HttpService, public activeModal: NgbActiveModal) {
        this.publication = new Publication();
    }

    createPublication(): void {
        // validate
        this.publication.invalidJournal = !this.publication.journal;
        this.publication.invalidLink = !this.publication.link;
        this.publication.invalidTitle = !this.publication.title;
        this.publication.invalidYear = !this.publication.year;
        this.publication.invalidAuthors = !this.publication.authors;

        if (this.publication.invalidJournal || this.publication.invalidLink || this.publication.invalidAuthors ||
            this.publication.invalidYear || this.publication.invalidTitle) {
            return;
        }

        this.http.post('hosts/' + this.host.id + '/publications', this.publication).subscribe((result: Publication) => {
            this.activeModal.close(result);
        });
    }
}
