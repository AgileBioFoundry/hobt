import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TierStatusComponent} from './tier-status.component';

describe('TierStatusComponent', () => {
    let component: TierStatusComponent;
    let fixture: ComponentFixture<TierStatusComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [TierStatusComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(TierStatusComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
