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
import {PublicationsComponent} from './components/host-detail/publications/publications.component';
import {ExperimentsComponent} from './components/host-detail/experiments/experiments.component';
import {AdminAttributesComponent} from './components/admin/attributes/admin-attributes.component';
import {
    CreateNewAttributeModalComponent
} from './components/admin/modal/create-new-attribute-modal/create-new-attribute-modal.component';
import {ApiComponent} from './components/admin/api/api.component';
import {ProtocolsComponent} from './components/host-detail/protocols/protocols.component';
import {AttributesComponent} from './components/host-detail/attributes/attributes.component';

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
        PublicationsComponent,
        ExperimentsComponent,
        AdminAttributesComponent,
        CreateNewAttributeModalComponent,
        ApiComponent,
        ProtocolsComponent,
        AttributesComponent
    ],
    imports: [
        BrowserModule,
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
