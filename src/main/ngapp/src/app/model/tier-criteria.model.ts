export class TierCriteria {
    label: string;
    available: boolean;

    constructor(label: string, available = false) {
        this.label = label;
        this.available = available;
    }
}
