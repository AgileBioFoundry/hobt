import {enableProdMode} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

import {environment} from './environments/environment';
import {bootstrapApplication} from "@angular/platform-browser";
import {AppComponent} from "./app/app.component";
import {provideRouter} from "@angular/router";
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {routes} from "./app/app-routing.module";

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
    providers: [
        provideRouter(routes),
        provideHttpClient(withInterceptorsFromDi()),
        // provideHttpClient(), // Provide HttpClient
        // {
        //     provide: HTTP_INTERCEPTORS,
        //     useClass: ErrorInterceptor,
        //     multi: true
        // }
    ],
})
    .catch(err => console.error(err));
