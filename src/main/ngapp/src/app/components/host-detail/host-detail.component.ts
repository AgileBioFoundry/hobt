import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Host} from "../../model/host.model";
import {Location} from "@angular/common";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {AddPermissionComponent} from "./modals/add-permission/add-permission.component";
import {Publication} from "../../model/Publication";
import {AddAttributeComponent} from "./modals/add-attribute/add-attribute.component";

@Component({
    selector: 'app-host-detail',
    templateUrl: './host-detail.component.html',
    styleUrls: ['./host-detail.component.css']
})
export class HostDetailComponent implements OnInit {

    host: Host;
    active: 'attributes';
    publications: Publication[];

    constructor(private route: ActivatedRoute, private location: Location, private modalService: NgbModal) {
        this.publications = [];
    }

    ngOnInit(): void {
        this.route.data.subscribe((data) => {
            this.host = data.host;
            if (!this.host) {
                this.host = new Host();
            }
        });

        const param = this.route.snapshot.params['attribute'];
        if (param)
            this.active = param;
    }

    activeChanged(event): void {
        this.location.go('/host/' + this.host.id + '/' + event.nextId);
    }

    showPublicationAddModal(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(AddPermissionComponent, options);
        modalRef.result.then((result: Publication) => {
            console.log(result);
            if (result)
                this.publications.push(result);
        }, error => {
            console.log('error', error);
        });
    }

    showAttributeAddModal(): void {
        const options: NgbModalOptions = {backdrop: 'static', keyboard: false};
        const modalRef = this.modalService.open(AddAttributeComponent, options);
        modalRef.result.then((result) => {
            console.log(result);
        }, error => {
            console.log('error', error);
        });
    }

    // tier1 =
    //     {
    //         cultivation: [new TierCriteria("Antibiotic Profile"), new TierCriteria("Growth Parameters"), new TierCriteria("BioSafety")],
    //         genome: [new TierCriteria("Vectors"), new TierCriteria("Selectable Markers"), new TierCriteria("Transformation")],
    //         expression: [new TierCriteria("Promoters"), new TierCriteria("Terminators"), new TierCriteria("Codon Optimization")],
    //         omics: [new TierCriteria("Annotated Genome")],
    //         libraries: [],
    //         models: [new TierCriteria("Simple Growth")]
    //     }
    //
    // tier2 =
    //     {
    //         cultivation: [new TierCriteria("Substrate Utilization"), new TierCriteria("Toxicity Profile"), new TierCriteria("Bioreactor Growth")],
    //         genome: [new TierCriteria("Genetic Stability"), new TierCriteria("Genome Integration"), new TierCriteria("Counter-selection"), new TierCriteria("Landing pads")],
    //         expression: [new TierCriteria("Induction System"), new TierCriteria("Panel of Parts")],
    //         omics: [new TierCriteria("Transcriptomic"), new TierCriteria("Proteomic"), new TierCriteria("Metabolomic")],
    //         libraries: [],
    //         models: [new TierCriteria("Genome-scale"), new TierCriteria("Promoters/RBSs"), new TierCriteria("Pan Genome")]
    //     }
    //
    // tier3 =
    //     {
    //         cultivation: [new TierCriteria("Cell Stress Monitor")],
    //         genome: [new TierCriteria("CRISPR-Cas"), new TierCriteria("Lambda Red"), new TierCriteria("Cre-lox"), new TierCriteria("Advanced Genome Integration")],
    //         expression: [new TierCriteria("BioSensors"), new TierCriteria("Expression Tuning"), new TierCriteria("Protein Localization"), new TierCriteria("Degradation Tags")],
    //         omics: [new TierCriteria("Lipidomic"), new TierCriteria("Glycomic"), new TierCriteria("Multi-omic"), new TierCriteria("Integration"), new TierCriteria("Protein Interactome"), new TierCriteria("C-metabolic flux")],
    //         libraries: [],
    //         models: [new TierCriteria("Kinetic"), new TierCriteria("Metabolic"), new TierCriteria("Population Balance")]
    //     }
    //
    // tier4 =
    //     {
    //         cultivation: [new TierCriteria("Scalability"), new TierCriteria("Baseline Strains"), new TierCriteria("Cell State Sensors")],
    //         genome: [],
    //         expression: [new TierCriteria("Dynamic Regulation")],
    //         omics: [new TierCriteria("Epigenetic")],
    //         libraries: [new TierCriteria("Deletion"), new TierCriteria("Loss of Function"), new TierCriteria("Overexpression"), new TierCriteria("Cell Sorted"), new TierCriteria("Adaptive Evolution")],
    //         models: [new TierCriteria("Signaling"), new TierCriteria("Gene Regulatory"), new TierCriteria("Predictive Cellular"), new TierCriteria("Multi-scale")]
    //     }
    //
    tiers = [];
}
