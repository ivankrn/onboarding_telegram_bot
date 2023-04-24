import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Alert, AlertType } from '../model/alert.model';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  private subject = new Subject<Alert>;

  onAlert(): Observable<Alert> {
    return this.subject.asObservable();
  }

  success(message: string) {
    this.alert(new Alert(message, AlertType.Success));
  }

  error(message: string) {
    this.alert(new Alert(message, AlertType.Error));
  }

  alert(alert: Alert) {
    this.subject.next(alert);
  }
}
