export class TierCriteria {
    label: string;
    available: boolean;
    status: number;

    constructor(label: string, available = false) {
        this.label = label;
        this.available = available;
        this.status = Math.floor(Math.random() * 4) + 1;  // todo remove

        switch (this.status) {
            case 1:
                this.status = 25;
                break;

            case 2:
                this.status = 50;
                break;

            case 3:
                this.status = 75;
                break;

            case 4:
                this.status = 100;
                break;
        }
    }
}
