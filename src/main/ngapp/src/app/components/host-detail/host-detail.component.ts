import {Component, OnInit} from '@angular/core';
import {TierCriteria} from "../../model/tier-criteria.model";

@Component({
    selector: 'app-host-detail',
    templateUrl: './host-detail.component.html',
    styleUrls: ['./host-detail.component.css']
})
export class HostDetailComponent implements OnInit {

    constructor() {
    }

    ngOnInit(): void {
    }

    tier1 =
        {
            cultivation: [new TierCriteria("Antibiotic Profile"), new TierCriteria("Growth Parameters"), new TierCriteria("BioSafety")],
            genome: [new TierCriteria("Vectors"), new TierCriteria("Selectable Markers"), new TierCriteria("Transformation")],
            expression: [new TierCriteria("Promoters"), new TierCriteria("Terminators"), new TierCriteria("Codon Optimization")],
            omics: [new TierCriteria("Annotated Genome")],
            libraries: [],
            models: [new TierCriteria("Simple Growth")]
        }

    tier2 =
        {
            cultivation: [new TierCriteria("Substrate Utilization"), new TierCriteria("Toxicity Profile"), new TierCriteria("Bioreactor Growth")],
            genome: [new TierCriteria("Genetic Stability"), new TierCriteria("Genome Integration"), new TierCriteria("Counter-selection"), new TierCriteria("Landing pads")],
            expression: [new TierCriteria("Induction System"), new TierCriteria("Panel of Parts")],
            omics: [new TierCriteria("Transcriptomic"), new TierCriteria("Proteomic"), new TierCriteria("Metabolomic")],
            libraries: [],
            models: [new TierCriteria("Genome-scale"), new TierCriteria("Promoters/RBSs"), new TierCriteria("Pan Genome")]
        }

    tier3 =
        {
            cultivation: [new TierCriteria("Cell Stress Monitor")],
            genome: [new TierCriteria("CRISPR-Cas"), new TierCriteria("Lambda Red"), new TierCriteria("Cre-lox"), new TierCriteria("Advanced Genome Integration")],
            expression: [new TierCriteria("BioSensors"), new TierCriteria("Expression Tuning"), new TierCriteria("Protein Localization"), new TierCriteria("Degradation Tags")],
            omics: [new TierCriteria("Lipidomic"), new TierCriteria("Glycomic"), new TierCriteria("Multi-omic"), new TierCriteria("Integration"), new TierCriteria("Protein Interactome"), new TierCriteria("C-metabolic flux")],
            libraries: [],
            models: [new TierCriteria("Kinetic"), new TierCriteria("Metabolic"), new TierCriteria("Population Balance")]
        }

    tier4 =
        {
            cultivation: [new TierCriteria("Scalability"), new TierCriteria("Baseline Strains"), new TierCriteria("Cell State Sensors")],
            genome: [],
            expression: [new TierCriteria("Dynamic Regulation")],
            omics: [new TierCriteria("Epigenetic")],
            libraries: [new TierCriteria("Deletion"), new TierCriteria("Loss of Function"), new TierCriteria("Overexpression"), new TierCriteria("Cell Sorted"), new TierCriteria("Adaptive Evolution")],
            models: [new TierCriteria("Signaling"), new TierCriteria("Gene Regulatory"), new TierCriteria("Predictive Cellular"), new TierCriteria("Multi-scale")]
        }

    tiers = [this.tier1];

    addRemoveTier(tier: any): void {
        this.tiers = [tier];
    }

    setCriteria(criteria: TierCriteria): void {
        criteria.available = !criteria.available;
    }
}
