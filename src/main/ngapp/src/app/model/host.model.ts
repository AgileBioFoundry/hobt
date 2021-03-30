export class Host {

    id: number;
    name: string;
    taxonomy: string;
    tier: number;
    publications: number = 0;
    parts: number = 0;
    experiments: number = 0;
    protocols: number = 0;
    updated: number = 0;

    constructor(id: number, name: string, taxonomy: string) {
        this.id = id;
        this.name = name;
        this.taxonomy = taxonomy;
        this.tier = 1;
    }
}
