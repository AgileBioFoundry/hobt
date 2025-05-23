import {Component} from '@angular/core';

@Component({
    selector: 'app-footer',
    standalone: true,
    templateUrl: './footer.component.html',
    styleUrls: ['./footer.component.css']
})
export class FooterComponent {

    constructor() {
    }

    getCurrentYear(): number {
        return new Date().getFullYear();
    }
}
