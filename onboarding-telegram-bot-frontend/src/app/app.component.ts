import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Onboarding Telegram bot';

  isSidebarOpen: boolean = false;
  isSidebarMobileOpen: boolean = false;

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  screenWidth = window.innerWidth;

  toggleSidebarMobile() {
    if (this.screenWidth < 800) {
      this.isSidebarMobileOpen = !this.isSidebarMobileOpen;
    }
  }

  toggleSidebarMobileButtons() {
    if (this.screenWidth < 800) {
      this.isSidebarMobileOpen = !this.isSidebarMobileOpen;
      this.isSidebarOpen = !this.isSidebarOpen;
    }
  }
}
