export class TierCriteria {

    id: number;
    label: string;
    available: boolean;
    status: number;
    description: string;

    // validation
    labelInvalid: boolean;
    detailsInvalid: boolean;
}
