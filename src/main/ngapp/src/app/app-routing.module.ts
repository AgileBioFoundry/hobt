import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {HostDetailComponent} from "./components/host-detail/host-detail.component";
import {MainPageComponent} from "./components/main-page/main-page.component";
import {HostDetailResolver} from "./components/host-detail/host-detail.resolver";

const routes: Routes = [
    { path: '', component: MainPageComponent },
    { path: 'login', component: LoginComponent },
    { path: 'host/:hid', redirectTo: 'host/:hid/attributes', pathMatch: 'full' },
    { path: 'host/:hid/:attribute', component: HostDetailComponent, resolve: { host: HostDetailResolver } }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
