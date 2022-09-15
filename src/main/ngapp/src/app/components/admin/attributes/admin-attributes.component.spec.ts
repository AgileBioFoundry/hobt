import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AdminAttributesComponent} from './admin-attributes.component';

describe('AttributesComponent', () => {
    let component: AdminAttributesComponent;
    let fixture: ComponentFixture<AdminAttributesComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [AdminAttributesComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(AdminAttributesComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
