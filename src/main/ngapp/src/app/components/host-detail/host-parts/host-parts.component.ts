import {Component, Input, OnInit} from '@angular/core';
import {Host} from "../../../model/host.model";
import {HttpService} from "../../../service/http.service";
import {SearchResult} from "../../../model/search-result";
import {Paging} from "../../../model/paging.model";

@Component({
    selector: 'app-host-parts',
    templateUrl: './host-parts.component.html',
    styleUrls: ['./host-parts.component.css']
})
export class HostPartsComponent implements OnInit {

    @Input() host: Host;
    loadingParts: boolean;
    results: SearchResult[];
    paging: Paging;

    constructor(private http: HttpService) {
    };

    ngOnInit(): void {
        this.loadingParts = true;
        this.paging = new Paging();

        this.http.get('hosts/' + this.host.id + '/parts').subscribe((result: any) => {
            this.loadingParts = false;
            this.results = result.results;
            this.paging.available = result.resultCount;

            console.log(result);
        }, error => {
            this.loadingParts = false;
        })
    }
}
