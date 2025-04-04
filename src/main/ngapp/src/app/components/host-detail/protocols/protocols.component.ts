import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {HttpService} from "../../../service/http.service";
import {Result} from "../../../model/result";
import {Paging} from "../../../model/paging.model";
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {NgbPagination} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-protocols',
    standalone: true,
    templateUrl: './protocols.component.html',
    imports: [
        NgClass,
        DatePipe,
        NgIf,
        NgForOf,
        NgbPagination
    ],
    styleUrls: ['./protocols.component.css']
})
export class ProtocolsComponent implements OnInit {

    @Input() host: Host;
    protocols: any[];
    paging: Paging;

    constructor(private http: HttpService) {
        this.paging = new Paging();
    }

    ngOnInit(): void {
        this.paging.processing = true;
        this.http.get('hosts/' + this.host.id + '/protocols', this.paging).subscribe((result: Result<any>) => {
            this.protocols = result.requested;
            this.paging.available = result.available;
            this.paging.processing = false;
        });
    }

    pageProtocols(page: number = 1): void {
        this.paging.start = ((page - 1) * this.paging.limit);
        this.paging.processing = true;
        this.http.get('hosts/' + this.host.id + '/protocols', this.paging).subscribe((result: Result<any>) => {
            this.protocols = result.requested;
            this.paging.available = result.available;
            this.paging.processing = false;
        }, error => {
            this.paging.processing = false;
        });
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

}
