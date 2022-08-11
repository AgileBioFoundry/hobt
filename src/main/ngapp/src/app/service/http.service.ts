import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {UserService} from './user.service';
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})

export class HttpService {

    private readonly apiUrl: string;

    private httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'}),
        params: new HttpParams()
    };

    constructor(private http: HttpClient, private userService: UserService) {
        this.apiUrl = environment.apiUrl;
    }

    private setHeaders(): void {
        if (this.userService.getUser()) {
            const sid = this.userService.getUser().sessionId;
            this.httpOptions.headers = new HttpHeaders({
                'Content-Type': 'application/json',
                'X-HOBT-Authentication-SessionId': sid
            });
        }
    }

    get<T>(api: string, options?): Observable<T> {
        this.setOptions(options);
        this.setHeaders();
        const url = `${this.apiUrl}/${api}`;
        return this.http.get<T>(url, this.httpOptions)
            .pipe(
                catchError(this.handleError<T>())
            );
    }

    post<T>(api: string, payload: T, options?): Observable<any> {
        this.setOptions(options);
        this.setHeaders();
        const url = `${this.apiUrl}/${api}`;
        return this.http.post<T>(url, payload, this.httpOptions);
    }

    delete<T>(api: string): Observable<any> {
        this.setOptions(undefined);
        this.setHeaders();
        const url = `${this.apiUrl}/${api}`;
        return this.http.delete(url, this.httpOptions);
    }

    put<T>(api: string, payload: T, options?): Observable<any> {
        this.setOptions(options);
        this.setHeaders();
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

            // redirect to the main page if user session expires
            // todo : might not be a good user option
            if (error.status === 401) {
                this.userService.clearUser();
                // this.router.navigate(['/']);
                return;
            }

            console.error(error); // log to console instead

            // Let the app keep running by returning an empty result.
            return of(result as T);
        };
    }
}
