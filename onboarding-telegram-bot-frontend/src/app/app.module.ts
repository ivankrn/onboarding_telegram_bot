import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule }   from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ArticleListComponent } from './component/article-list/article-list/article-list.component';
import { ArticleFormComponent } from './component/article-form/article-form.component';
import { ArticleService } from './service/article.service';
import { HttpClientModule } from '@angular/common/http';
import { TestListComponent } from './component/test-list/test-list/test-list.component';
import { DashboardComponent } from './component/dashboard/dashboard/dashboard.component';
import { TestFormComponent } from './component/test-form/test-form/test-form.component';

@NgModule({
  declarations: [
    AppComponent,
    ArticleListComponent,
    ArticleFormComponent,
    TestListComponent,
    DashboardComponent,
    TestFormComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [ArticleService],
  bootstrap: [AppComponent]
})
export class AppModule { }
