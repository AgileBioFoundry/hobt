import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {HostDetailComponent} from "./components/host-detail/host-detail.component";
import {MainPageComponent} from "./components/main-page/main-page.component";
import {HostDetailResolver} from "./components/host-detail/host-detail.resolver";
import {SettingsComponent} from "./components/admin/settings/settings.component";

const routes: Routes = [
    {path: '', component: MainPageComponent},
    {path: 'login', component: LoginComponent},
    {path: 'admin', redirectTo: 'admin/tiers', pathMatch: 'full'},
    {path: 'host/:hid', redirectTo: 'host/:hid/attributes', pathMatch: 'full'},
    {path: 'admin/:subsection', component: SettingsComponent},
    {path: 'host/:hid/:attribute', component: HostDetailComponent, resolve: {host: HostDetailResolver}}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {relativeLinkResolution: 'legacy'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
