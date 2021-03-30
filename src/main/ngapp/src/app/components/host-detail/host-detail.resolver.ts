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
        // TODO
        const host: Host = this.http.getHostById(route.params.hid);
        return new Observable(observer => {
            observer.next(host)
            observer.complete()
        });
        // return this.http.get('hosts/' + route.params.designId);
    }
}
