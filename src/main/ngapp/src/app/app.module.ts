import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {HostDetailComponent} from './components/host-detail/host-detail.component';
import {MainPageComponent} from './components/main-page/main-page.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {CriteriaComponent} from './components/host-detail/criteria/criteria.component';
import {HostDetailResolver} from "./components/host-detail/host-detail.resolver";

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        HeaderComponent,
        FooterComponent,
        HostDetailComponent,
        MainPageComponent,
        CriteriaComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgbModule,
        HttpClientModule,
        BrowserModule,
        AppRoutingModule,
        FormsModule
    ],
    providers: [HostDetailResolver],
    bootstrap: [AppComponent]
})
export class AppModule {
}
