import {Host} from "./host.model";

export class Attribute {
    id: number;
    label: string;
    type: string;
    required: boolean;
    allOrganisms: boolean;
    value: string;
    hosts: Host[];
}
