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
import {AuthenticationComponent} from "./components/admin/authentication/authentication.component";
import {AttributesComponent} from "./components/admin/attributes/attributes.component";
import {ApiComponent} from "./components/admin/api/api.component";

const routes: Routes = [
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
                component: AttributesComponent
            }, {
                path: 'auth',
                component: AuthenticationComponent
            }, {
                path: 'api',
                component: ApiComponent
            }
        ]
    },
    {path: 'host/:hid', redirectTo: 'host/:hid/attributes', pathMatch: 'full'},
    {path: 'host/:hid/:attribute', component: HostDetailComponent, resolve: {host: HostDetailResolver}}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {relativeLinkResolution: 'legacy'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
