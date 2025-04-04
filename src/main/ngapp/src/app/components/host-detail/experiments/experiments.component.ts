import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {HttpService} from "../../../service/http.service";
import {Study} from "../../../model/study.model";
import {DatePipe, NgForOf, NgIf} from "@angular/common";

@Component({
    selector: 'app-experiments',
    standalone: true,
    templateUrl: './experiments.component.html',
    imports: [
        DatePipe,
        NgIf,
        NgForOf
    ],
    styleUrls: ['./experiments.component.css']
})
export class ExperimentsComponent implements OnInit {

    @Input() host: Host;
    studies: Study[];
    processing: boolean;

    constructor(private http: HttpService) {
    }

    ngOnInit(): void {
        this.processing = true;
        this.http.get('hosts/' + this.host.id + '/experiments').subscribe((result: Study[]) => {
                this.studies = result;
                this.processing = false;
            }, error => {
                this.processing = false;
            }
        )
    }
}
