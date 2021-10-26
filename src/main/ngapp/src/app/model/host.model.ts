import {Tier} from "./tier.model";

export class Host {

    id: number;
    name: string;
    phylum: string;
    tier: Tier;

    publications: number = 0;
    parts: number = 0;
    experiments: number = 0;
    protocols: number = 0;

    created: number = 0;
    updated: number = 0;
}
