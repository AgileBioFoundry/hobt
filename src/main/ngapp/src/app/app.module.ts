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
import {AddPublicationComponent} from './components/host-detail/modals/add-publication/add-publication.component';
import {AddAttributeComponent} from './components/host-detail/modals/add-attribute/add-attribute.component';
import {SettingsComponent} from './components/admin/settings/settings.component';
import {UsersComponent} from './components/admin/users/users.component';
import {TierStatusComponent} from './components/tiers/tier-status/tier-status.component';
import {HostTiersComponent} from './components/host-detail/host-tiers/host-tiers.component';
import {TiersComponent} from "./components/admin/tiers/tiers.component";
import {RegisterComponent} from './components/register/register.component';
import {RolesComponent} from './components/admin/roles/roles.component';
import {CreateRoleModalComponent} from './components/admin/modal/create-role-modal/create-role-modal.component';
import {AdminComponent} from "./components/admin/admin.component";
import {AddRoleComponent} from './components/admin/users/add-role/add-role.component';
import {HostPartsComponent} from './components/host-detail/host-parts/host-parts.component';
import {ConfirmActionComponent} from './components/common/confirm-action/confirm-action.component';
import {PublicationsComponent} from './components/host-detail/publications/publications.component';
import { AuthenticationComponent } from './components/admin/authentication/authentication.component';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        HeaderComponent,
        FooterComponent,
        HostDetailComponent,
        MainPageComponent,
        CriteriaComponent,
        AddPublicationComponent,
        AddAttributeComponent,
        SettingsComponent,
        UsersComponent,
        TiersComponent,
        TierStatusComponent,
        HostTiersComponent,
        RegisterComponent,
        RolesComponent,
        CreateRoleModalComponent,
        AdminComponent,
        AddRoleComponent,
        HostPartsComponent,
        ConfirmActionComponent,
        PublicationsComponent,
        AuthenticationComponent
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
