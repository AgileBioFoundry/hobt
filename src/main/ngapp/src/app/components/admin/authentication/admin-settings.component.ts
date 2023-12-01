import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../../service/http.service";
import {FormsModule} from "@angular/forms";
import {NgbTooltip} from "@ng-bootstrap/ng-bootstrap";
import {JsonPipe, NgForOf, NgIf} from "@angular/common";
import {Setting} from "../../../model/setting";

@Component({
    selector: 'app-authentication',
    standalone: true,
    templateUrl: './admin-settings.component.html',
    imports: [
        FormsModule,
        NgbTooltip,
        NgForOf,
        NgIf,
        JsonPipe
    ],
    styleUrls: ['./admin-settings.component.css']
})
export class AdminSettingsComponent implements OnInit {

    processing: boolean;
    options: string[] = ['DEFAULT', 'LDAP', 'OPEN'];

    smtpSetting: Setting = new Setting('SMTP_HOST');
    authSetting: Setting = new Setting('AUTHENTICATION_METHOD', 'DEFAULT');
    adminEmailSetting: Setting = new Setting('ADMIN_EMAIL');
    sendEmailSetting: Setting = new Setting('FROM_EMAIL');

    constructor(private http: HttpService) {
    }

    ngOnInit(): void {
        // the following can be done more efficiently. dev speed is being preferred for now

        // get smtp host.value
        this.getSettingValue(this.smtpSetting);

        // get admin email setting
        this.getSettingValue(this.adminEmailSetting);

        // get sender email setting
        this.getSettingValue(this.sendEmailSetting);

        // get auth setting
        this.getSettingValue(this.authSetting);
    }

    getSettingValue(setting: Setting): void {
        this.http.get('settings/' + setting.key).subscribe({
            next: (result: Setting) => {
                if (result && result.value)
                    setting.value = result.value;
            }
        })
    }

    selectionChange(): void {
        if (!this.authSetting.value)
            return;

        this.http.put('settings', this.authSetting).subscribe({
            next: (result: Setting) => {
                this.authSetting.enableEdit = false;
            }, error: () => {
                this.authSetting.enableEdit = false;
            }
        });
    }

    enableDisableEdit(setting: Setting): void {
        setting.enableEdit = !setting.enableEdit;
    }

    updateSetting(setting: Setting): void {
        this.http.put('settings', setting).subscribe({
            next: (result: Setting) => {
                setting.value = result.value;
                setting.enableEdit = false;
            }
        });
    }
}
