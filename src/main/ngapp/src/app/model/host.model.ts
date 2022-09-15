import {Tier} from "./tier.model";
import {HostStatistics} from "./host-statistics";
import {Attribute} from "./attribute.model";

export class Host {

    id: number;
    name: string;
    phylum: string;
    tier: Tier;

    statistics: HostStatistics;

    // publications: number = 0;
    // parts: number = 0;
    // experiments: number = 0;
    // protocols: number = 0;

    created: number = 0;
    updated: number = 0;

    // ui controls
    selected: boolean;
    attributes: Attribute[];
}
