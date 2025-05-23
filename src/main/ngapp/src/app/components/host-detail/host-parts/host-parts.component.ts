import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {HttpService} from "../../../service/http.service";
import {SearchResult} from "../../../model/search-result";
import {Paging} from "../../../model/paging.model";
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {NgbPagination} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-host-parts',
    standalone: true,
    templateUrl: './host-parts.component.html',
    imports: [
        NgClass,
        NgIf,
        NgbPagination,
        DatePipe,
        NgForOf
    ],
    styleUrls: ['./host-parts.component.css']
})
export class HostPartsComponent implements OnInit {

    @Input() host: Host;
    @Input() strains: boolean;

    results: SearchResult[];
    paging: Paging;

    constructor(private http: HttpService) {
        this.paging = new Paging();
    };

    ngOnInit(): void {
        this.pageParts();
    }

    pageParts(page: number = 1): void {
        this.paging.start = ((page - 1) * this.paging.limit);
        this.paging.processing = true;

        let url = 'hosts/' + this.host.id + '/parts';
        if (this.strains)
            url += "?strainsOnly=true"

        this.http.get(url, this.paging).subscribe({
            next: (result: any) => {
                this.results = result.results;
                this.paging.available = result.resultCount;
                this.paging.processing = false;
            }
            , error: (error: any) => {
                this.paging.processing = false;
            }
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
}
