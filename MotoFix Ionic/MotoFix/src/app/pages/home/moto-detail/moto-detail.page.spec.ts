import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MotoDetailPage } from './moto-detail.page';

describe('MotoDetailPage', () => {
  let component: MotoDetailPage;
  let fixture: ComponentFixture<MotoDetailPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(MotoDetailPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
