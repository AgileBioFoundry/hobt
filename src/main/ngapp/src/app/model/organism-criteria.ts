import {Host} from "./host.model";
import {TierCriteria} from "./tier-criteria.model";

export class OrganismCriteria {
    organism: Host[];
    criteria: TierCriteria[];
    percentageComplete: number;
    created: number;
    updated: number;
}
