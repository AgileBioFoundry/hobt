import {Component, OnInit} from '@angular/core';
import {Host} from "../../model/host.model";
import {Router, RouterLink} from "@angular/router";
import {HttpService} from "../../service/http.service";
import {Tier} from "../../model/tier.model";
import {PermissionService} from "../../service/permission.service";
import {HostStatistics} from "../../model/host-statistics";
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HeaderComponent} from "../header/header.component";
import {NgbDropdown, NgbDropdownItem, NgbDropdownMenu, NgbDropdownToggle} from "@ng-bootstrap/ng-bootstrap";
import {FooterComponent} from "../footer/footer.component";

@Component({
    selector: 'app-main-page',
    templateUrl: './main-page.component.html',
    standalone: true,
    imports: [
        DatePipe,
        NgForOf,
        NgIf,
        NgClass,
        FormsModule,
        RouterLink,
        NgbDropdown,
        NgbDropdownToggle,
        NgbDropdownItem,
        FooterComponent,
        NgbDropdownMenu,
        HeaderComponent
    ],
    styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

    gridMode: boolean = true;
    params = {asc: false, sort: "id", limit: 100};
    newHost: Host;
    hosts: Host[];
    showCreateNewHost = false;
    availableTiers: Tier[];

    constructor(private router: Router, private http: HttpService, private permissionService: PermissionService) {
        this.hosts = [];
        this.newHost = new Host();
    }

    ngOnInit(): void {
        this.getHosts();
    }

    // get available tiers
    private getTiers(): void {
        this.http.get('tiers').subscribe((result: Tier[]) => {
            this.availableTiers = result;
        });
    }

    private getHosts(): void {
        this.http.get('hosts', this.params).subscribe((result: any) => {
            if (!result || !result.requested)
                return;

            this.hosts = result.requested.sort((a: Host, b: Host) => a.id - b.id);

            for (const host of this.hosts) {
                this.http.get('hosts/' + host.id + '/statistics').subscribe((result: HostStatistics) => {
                    host.statistics = result;
                });
            }
        });
    }

    sort(type: string): void {
        if (this.params.sort === type) {
            this.params.asc = !this.params.asc;
        } else {
            this.params.sort = type;
        }

        const multiplier = this.params.asc ? -1 : 1;
        switch (type) {
            default:
                this.hosts.sort((a, b) => (a[type] > b[type]) ? 1 : (a[type] === b[type]) ? ((a[type] > b[type]) ? 1 : -1) : -1);
                break;

            case 'tier':
                this.hosts.sort((a, b) => multiplier * this.sortTiers(a, b));
                break;

            case 'taxonomy':
                this.hosts.sort((a, b) => multiplier * (a.phylum.localeCompare(b.phylum)));
                break;
        }
    }

    private sortTiers(a: Host, b: Host): number {
        // this sort function assumes that missing .tier is equivalent to a.tier.index === 0 is true
        if (!a.tier) return -1;
        if (!b.tier) return 1;

        return a.tier.index - b.tier.index;
    }

    goToHost(host: Host): void {
        this.router.navigate(['host/', host.id]);
    }

    createOrganism(): void {
        this.http.post('hosts', this.newHost).subscribe((created: Host) => {
            // this.getHosts();
            this.hosts.push(created);
            this.showCreateNewHost = false;
            this.newHost = new Host();
        });
    }

    showCreateOrganism(): void {
        this.showCreateNewHost = true;
        this.getTiers();
    }

    canCreateNewHost(): boolean {
        return this.permissionService.canWrite(this.permissionService.HOSTS);
    }
}
