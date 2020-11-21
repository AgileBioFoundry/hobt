import {TierCriteria} from "./tier-criteria.model";

export class Tier {
    cultivation: [TierCriteria];
    genome: [TierCriteria];
    expression: [TierCriteria];
    omics: [TierCriteria];
    libraries: [TierCriteria];
    models: [TierCriteria];
}
