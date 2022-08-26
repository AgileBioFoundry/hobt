import {Injectable} from '@angular/core';
import {User} from "../model/user.model";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private user: User;
    private USER_KEY: string = 'hobt-user';

    constructor() {
    }

    setUser(user: User) {
        this.user = user;
        sessionStorage.setItem(this.USER_KEY, JSON.stringify(this.user));
    }

    isAdmin(): boolean {
        const user: User = this.getUser();
        if (!user) {
            return false;
        }

        return user.isAdmin;
    }

    getUser(): User {
        // if user is not set, then attempt to retrieve from local storage
        if (!this.user) {
            this.user = JSON.parse(sessionStorage.getItem(this.USER_KEY));
        }

        if (!this.user) {
            this.clearUser();
        }

        return this.user;
    }

    clearUser(): void {
        sessionStorage.removeItem(this.USER_KEY);
        this.user = undefined;
    }
}
