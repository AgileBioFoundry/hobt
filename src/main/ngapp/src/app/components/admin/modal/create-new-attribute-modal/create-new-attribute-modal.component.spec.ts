import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewAttributeModalComponent } from './create-new-attribute-modal.component';

describe('CreateNewAttributeModalComponent', () => {
  let component: CreateNewAttributeModalComponent;
  let fixture: ComponentFixture<CreateNewAttributeModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateNewAttributeModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateNewAttributeModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
