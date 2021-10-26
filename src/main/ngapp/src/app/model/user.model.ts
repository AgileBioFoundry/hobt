import {Role} from "./role.model";

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
    public creationTime: string;
    public lastUpdateTime: string;
    public lastLoginTime: string;
    public disabled: boolean;
    public description: string;
    updatingActiveStatus?: boolean;
    allowedToChangePassword: boolean;
    usingTemporaryPassword: boolean;
    type?: string;
    isAdmin: boolean;
}
