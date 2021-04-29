import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {HttpService} from "../../service/http.service";
import {Observable} from "rxjs";
import {Host} from "../../model/host.model";

@Injectable()
export class HostDetailResolver implements Resolve<any> {

    constructor(private http: HttpService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Host> {
        return this.http.get('hosts/' + route.params.hid);
    }
}
