import {Permission} from "./permission.model";
import {User} from "./user.model";

export class Role {
    id: number;
    label: string;
    description: string;
    permissions: Permission[];
    members: User[];

    // ui toggle
    showAddMembers?: boolean;
    showAddPermissions?: boolean;

    constructor() {
        this.members = [];
        this.permissions = [];
    }
}
