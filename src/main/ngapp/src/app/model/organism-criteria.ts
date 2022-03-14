import {TierCriteria} from "./tier-criteria.model";

export class OrganismCriteria {
    id: number;
    criteria: TierCriteria;
    percentageComplete: number; // aka status
    created: number;
    updated: number;
}
