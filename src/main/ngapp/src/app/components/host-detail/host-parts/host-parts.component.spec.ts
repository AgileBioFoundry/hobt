import {ComponentFixture, TestBed} from '@angular/core/testing';

import {HostPartsComponent} from './host-parts.component';

describe('HostPartsComponent', () => {
    let component: HostPartsComponent;
    let fixture: ComponentFixture<HostPartsComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [HostPartsComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(HostPartsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
