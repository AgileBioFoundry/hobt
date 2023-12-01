export class Setting {
    key: string;
    value: string;
    enableEdit: boolean;
    label: string;

    constructor(k: string, v: string = '') {
        this.key = k;
        this.value = v;
        this.enableEdit = false;
    }
}
