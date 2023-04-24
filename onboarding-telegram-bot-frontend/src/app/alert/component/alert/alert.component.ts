import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Alert, AlertType } from '../../model/alert.model';
import { AlertService } from '../../service/alert.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css'],
  animations: [
    trigger("fade", [
      state("void, hidden",
      style({opacity: 0})),
      transition("* => void, * => hidden", animate("500ms"))
    ])
  ]
})
export class AlertComponent implements OnInit, OnDestroy {

  alerts: Alert[] = [];
  alertSub: Subscription;

  constructor(private alertService: AlertService) { }

  ngOnInit() {
    this.alertSub = this.alertService.onAlert().subscribe(alert => {
      this.alerts.push(alert);
      setTimeout(() => this.removeAlert(alert), 3000);
    })
  }

  ngOnDestroy() {
    this.alertSub.unsubscribe();
  }

  removeAlert(alert: Alert) {
    this.alerts = this.alerts.filter(a => a !== alert);
  }

  cssClass(alert: Alert) {
    if (!alert) {
      return;
    }
    const classes = ["alert", "alert-dismissible"];
    const alertTypeClass = {
      [AlertType.Success]: "alert-success",
      [AlertType.Error]: "alert-danger"
    };
    classes.push(alertTypeClass[alert.type]);
    return classes.join(" ");
  }
}
