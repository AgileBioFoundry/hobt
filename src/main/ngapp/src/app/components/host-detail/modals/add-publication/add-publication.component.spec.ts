import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddPublicationComponent} from './add-publication.component';

describe('AddPermissionComponent', () => {
    let component: AddPublicationComponent;
    let fixture: ComponentFixture<AddPublicationComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [AddPublicationComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AddPublicationComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
