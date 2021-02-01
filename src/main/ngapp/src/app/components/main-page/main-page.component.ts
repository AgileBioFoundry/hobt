import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-main-page',
    templateUrl: './main-page.component.html',
    styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

    gridMode: boolean = true;
    params = { asc: false, sort: "updated" };

    constructor() {
    }

    ngOnInit(): void {
    }

    sort(type: string): void {
    }

    hosts = [
        {
            name: "Pseudomonas putida",
            taxonomy: "Gammaproteobacteria",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Rhodosporidium  toruloides",
            taxonomy: "Basidiomycota",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Aspergillus niger",
            taxonomy: "Ascomycota",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Corynebacterium glutamicum",
            taxonomy: "Actinobacteria",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Cupriavidus necator",
            taxonomy: "Betaproteobacteria",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Bacillus coagulans",
            taxonomy: "Firmicutes",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Pichia kudriavzevii",
            taxonomy: "Ascomycota",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Clostridium tyrobutyricum",
            taxonomy: "Firmicutes",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Lipomyces starkeyi",
            taxonomy: "Ascomycota",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Rhodobacter sphaeroides",
            taxonomy: "Alphaproteobacteria",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Aspergillus pseudoterreus",
            taxonomy: "Ascomycota",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Zymomonas mobilis",
            taxonomy: "Alphaproteobacteria",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Clostridium ljungdahlii",
            taxonomy: "Firmicutes",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Zygosaccharomyces bailii",
            taxonomy: "Ascomycota",
            tier: Math.floor(Math.random() * 4) + 1
        },
        {
            name: "Methylomicrobium buryatense",
            taxonomy: "Gammaproteobacteria",
            tier: Math.floor(Math.random() * 4) + 1
        }
    ];

}
