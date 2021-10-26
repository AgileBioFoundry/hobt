import {TierCriteria} from "./tier-criteria.model";
import {TierRule} from "./tier-rules.model";

export class Tier {

    id: number;
    label: string;
    index: number;
    completed: boolean;
    criteria: Array<TierCriteria>;
    rules: Array<TierRule>;

    // ui options
    showAddCriteria: boolean;
    showAddRules: boolean;
    collapsed: boolean;

    constructor(index: number) {
        this.index = index;
    }
}
