import {Component, OnInit} from '@angular/core';
import {Host} from "../../model/host.model";
import {Router} from "@angular/router";
import {HttpService} from "../../service/http.service";

@Component({
    selector: 'app-main-page',
    templateUrl: './main-page.component.html',
    styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

    gridMode: boolean = true;
    params = { asc: false, sort: "id", limit: 15 };
    newHost: Host;
    hosts: Host[];
    showCreateNewHost = false;

    constructor(private router: Router, private http: HttpService) {
        this.hosts = [];
        this.newHost = new Host();
    }

    ngOnInit(): void {
        this.getHosts();

        // this.http.post('hosts', {}).subscribe((result) => {
        //     console.log(result);
        // });
    }

    private getHosts(): void {
        this.http.get('hosts', this.params).subscribe((result: any) => {
            if (!result || !result.requested)
                return;

            this.hosts = result.requested;
        });
    }

    sort(type: string): void {
        if (this.params.sort === type) {
            this.params.asc = !this.params.asc;
        } else {
            this.params.sort = type;
        }

        this.hosts.sort((a, b) => (a[type] > b[type]) ? 1 : (a[type] === b[type]) ? ((a[type] > b[type]) ? 1 : -1) : -1);
    }

    goToHost(host: Host): void {
        this.router.navigate(['host/', host.id]);
    }

    createOrganism(): void {
        this.http.post('hosts', this.newHost).subscribe((result) => {
            this.getHosts();
            this.showCreateNewHost = false;
            this.newHost = new Host();
        });
    }

    showCreateOrganism(): void {
        this.showCreateNewHost = true;
    }

    // hosts = [
    // new Host(1, "Pseudomonas putida", "Gammaproteobacteria"),
    // new Host(2, "Rhodosporidium toruloides", "Basidiomycota"),
    // new Host(3, "Aspergillus niger", "Ascomycota"),
    // new Host(4, "Corynebacterium glutamicum", "Actinobacteria"),
    // new Host(5, "Cupriavidus necator", "Betaproteobacteria"),
    // new Host(6, "Bacillus coagulans", "Firmicutes"),
    // new Host(7, "Pichia kudriavzevii", "Ascomycota"),
    // new Host(8, "Clostridium tyrobutyricum", "Firmicutes"),
    // new Host(9, "Lipomyces starkeyi", "Ascomycota"),
    // new Host(10, "Rhodobacter sphaeroides", "Alphaproteobacteria"),
    // new Host(11, "Aspergillus pseudoterreus", "Ascomycota"),
    // new Host(12, "Zymomonas mobilis", "Alphaproteobacteria"),
    // new Host(13, "Clostridium ljungdahlii", "Firmicutes"),
    // new Host(14, "Zygosaccharomyces bailii", "Ascomycota"),
    // // new Host(15, "Methylomicrobium buryatense", "Gammaproteobacteria")
    // ];
}
