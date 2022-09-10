import {Host} from "./host.model";

export class Attribute {
    label: string;
    type: string;
    required: boolean;
    allOrganisms: boolean;
    hosts: Host[];
}
