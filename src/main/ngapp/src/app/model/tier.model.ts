import {TierCriteria} from "./tier-criteria.model";

export class Tier {

    id: number;
    label: string;
    index: number;
    criteria: Array<TierCriteria>;

    showAddCriteria: boolean;

    constructor(index: number) {
        this.index = index;
    }
}
