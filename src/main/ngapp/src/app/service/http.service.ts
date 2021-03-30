import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {UserService} from './user.service';
import {Router} from '@angular/router';
import {environment} from "../../environments/environment";
import {Host} from "../model/host.model";

@Injectable({
    providedIn: 'root'
})

export class HttpService {

    private readonly apiUrl: string;

    hosts = [
        new Host(1, "Pseudomonas putida", "Gammaproteobacteria"),
        new Host(2, "Rhodosporidium toruloides", "Basidiomycota"),
        new Host(3, "Aspergillus niger", "Ascomycota"),
        new Host(4, "Corynebacterium glutamicum", "Actinobacteria"),
        new Host(5, "Cupriavidus necator", "Betaproteobacteria"),
        new Host(6, "Bacillus coagulans", "Firmicutes"),
        new Host(7, "Pichia kudriavzevii", "Ascomycota"),
        new Host(8, "Clostridium tyrobutyricum", "Firmicutes"),
        new Host(9, "Lipomyces starkeyi", "Ascomycota"),
        new Host(10, "Rhodobacter sphaeroides", "Alphaproteobacteria"),
        new Host(11, "Aspergillus pseudoterreus", "Ascomycota"),
        new Host(12, "Zymomonas mobilis", "Alphaproteobacteria"),
        new Host(13, "Clostridium ljungdahlii", "Firmicutes"),
        new Host(14, "Zygosaccharomyces bailii", "Ascomycota"),
        new Host(15, "Methylomicrobium buryatense", "Gammaproteobacteria")
    ];

    private httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        params: new HttpParams()
    };

    constructor(private http: HttpClient, private userService: UserService, private router: Router) {
        this.apiUrl = environment.apiUrl;

        // this.get('settings/register').subscribe(result => {
        //     console.log(result);
        // }, error => {
        //     console.error(error);
        // });
    }

    getHostById(id: number): Host {
        for (const host of this.hosts) {
            if (host.id == id)
                return host;
        }
        return undefined;
    }

    get<T>(api: string, options?, redirect?): Observable<T> {
        this.setOptions(options);
        if (this.userService.getUser(redirect)) {
            const sid = this.userService.getUser().sessionId;
            this.httpOptions.headers = new HttpHeaders({
                'Content-Type': 'application/json',
                'X-HOBT-Authentication-SessionId': sid
            });
        }

        const url = `${this.apiUrl}/${api}`;
        return this.http.get<T>(url, this.httpOptions)
            .pipe(
                catchError(this.handleError<T>())
            );
    }

    post<T>(api: string, payload: T, options?): Observable<any> {
        this.setOptions(options);
        const url = `${this.apiUrl}/${api}`;
        return this.http.post<T>(url, payload, this.httpOptions);
    }

    delete<T>(api: string): Observable<any> {
        const url = `${this.apiUrl}/${api}`;
        return this.http.delete(url, this.httpOptions);
    }

    put<T>(api: string, payload: T, options?): Observable<any> {
        this.setOptions(options);
        const url = `${this.apiUrl}/${api}`;
        return this.http.put(url, payload, this.httpOptions);
    }

    private setOptions(options): void {
        this.httpOptions.params = new HttpParams();
        if (!options) {
            return;
        }

        for (const prop in options) {
            if (!options.hasOwnProperty(prop)) {
                continue;
            }

            this.httpOptions.params = this.httpOptions.params.append(prop, options[prop]);
        }
    }

    private handleError<T>(result?) {
        return (error: any): Observable<T> => {

            // TODO: send the error to remote logging infrastructure
            if (error.status === 401) {
                this.router.navigate(['/login']);
                return;
            }

            console.error(error); // log to console instead

            // Let the app keep running by returning an empty result.
            return of(result as T);
        };
    }
}
