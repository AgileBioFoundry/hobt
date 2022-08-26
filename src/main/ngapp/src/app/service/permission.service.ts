import {Injectable} from '@angular/core';
import {Permission} from "../model/permission.model";
import {UserService} from "./user.service";

@Injectable({
    providedIn: 'root'
})
export class PermissionService {

    private permissions: Permission[];
    TIERS: string = 'Tiers';
    HOSTS: string = 'Hosts';

    constructor(private userService: UserService) {
        this.permissions = [];
    }

    setPermissions(permissions: Permission[]): void {
        this.permissions = permissions;
    }

    canRead(resource: string): boolean {
        if (this.userService.isAdmin())
            return true;

        for (let permission of this.permissions) {
            if (permission.resource === resource)
                return true;
        }
        return false;
    }

    canWrite(resource: string): boolean {
        if (this.userService.isAdmin())
            return true;

        for (let permission of this.permissions) {
            if (permission.resource === resource)
                return true;
        }
        return false;
    }
}
