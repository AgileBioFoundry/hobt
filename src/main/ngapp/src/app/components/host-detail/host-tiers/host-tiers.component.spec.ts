import {ComponentFixture, TestBed} from '@angular/core/testing';

import {HostTiersComponent} from './host-tiers.component';

describe('HostTiersComponent', () => {
    let component: HostTiersComponent;
    let fixture: ComponentFixture<HostTiersComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [HostTiersComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(HostTiersComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
