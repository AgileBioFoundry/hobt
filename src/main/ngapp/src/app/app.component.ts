import {Component} from '@angular/core';
import {RouterOutlet} from "@angular/router";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    standalone: true,
    imports: [
        RouterOutlet,
    ],
    styleUrls: ['./app.component.css']
})

export class AppComponent {

    title = 'Host Onboarding Tool';

    // constructor(private router: Router) {
        // this.router.events.subscribe((e) => {
            // if (e instanceof NavigationStart) {
            //     if (e.id === 1) {
            //         const url = e.url;
            //         if (url !== '/login') {
            //             this.user.setLoginRedirect(url);
            //         }
            //     }
            // }
        // });
    // }
}
