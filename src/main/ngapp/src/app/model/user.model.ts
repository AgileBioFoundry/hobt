import {Role} from "./role.model";
import {Permission} from "./permission.model";

export class User {
    id?: number;
    userId?: string;
    firstName: string;
    lastName: string;
    email: string;
    password?: string;
    newPassword?: string;
    sessionId?: string;
    roles: Role[];
    permissions: Permission[];
    public creationTime: string;
    public lastUpdateTime: string;
    public lastLoginTime: string;
    public isDisabled: boolean;
    public description: string;
    allowedToChangePassword: boolean;
    usingTemporaryPassword: boolean;
    type?: string;
    institution?: string;
    commercial: boolean;

    // validation ui controls
    userIdValid: boolean;
    institutionValid: boolean;
    firstNameValid: boolean;
    lastNameValid: boolean;
    descriptionValid: boolean;
    isAdmin: boolean;

    processing: boolean;

    constructor() {
        this.usingTemporaryPassword = false;

        this.userIdValid = true;
        this.institutionValid = true;
        this.firstNameValid = true;
        this.lastNameValid = true;
        this.descriptionValid = true;
    }
}
