import {Host} from "./host.model";
import {AttributeOption} from "./attribute-option.model";

export class Attribute {
    id: number;
    label: string;
    type: string;
    required: boolean;
    allOrganisms: boolean;
    value: string;
    hosts: Host[];
    options: AttributeOption[];
}
