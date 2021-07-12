import {TierCriteria} from "./tier-criteria.model";

export class Tier {

    id: number;
    label: string;
    index: number;
    completed: boolean;
    criteria: Array<TierCriteria>;

    // ui options
    showAddCriteria: boolean;
    collapsed: boolean;

    constructor(index: number) {
        this.index = index;
    }
}
