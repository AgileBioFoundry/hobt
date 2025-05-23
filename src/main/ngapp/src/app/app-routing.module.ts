import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {HostDetailComponent} from "./components/host-detail/host-detail.component";
import {MainPageComponent} from "./components/main-page/main-page.component";
import {HostDetailResolver} from "./components/host-detail/host-detail.resolver";
import {AdminGuardGuard} from "./components/admin/admin-guard.guard";
import {AdminComponent} from "./components/admin/admin.component";
import {TiersComponent} from "./components/admin/tiers/tiers.component";
import {UsersComponent} from "./components/admin/users/users.component";
import {RolesComponent} from "./components/admin/roles/roles.component";
import {AdminSettingsComponent} from "./components/admin/authentication/admin-settings.component";
import {AdminAttributesComponent} from "./components/admin/attributes/admin-attributes.component";
import {ApiComponent} from "./components/admin/api/api.component";
import {RegisterComponent} from "./components/register/register.component";

export const routes: Routes = [
    {path: '', component: MainPageComponent},
    {path: 'login', component: LoginComponent},
    {
        path: 'admin', component: AdminComponent, canActivate: [AdminGuardGuard],
        children: [
            {
                path: '', redirectTo: 'tiers', pathMatch: 'full'
            }, {
                path: 'tiers',
                component: TiersComponent
            }, {
                path: 'users',
                component: UsersComponent
            }, {
                path: 'roles',
                component: RolesComponent
            }, {
                path: 'attributes',
                component: AdminAttributesComponent
            }, {
                path: 'settings',
                component: AdminSettingsComponent
            }, {
                path: 'api',
                component: ApiComponent
            }
        ]
    },
    {path: 'host/:hid', redirectTo: 'host/:hid/attributes', pathMatch: 'full'},
    {path: 'host/:hid/:attribute', component: HostDetailComponent, resolve: {host: HostDetailResolver}},
    {path: 'register', component: RegisterComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
