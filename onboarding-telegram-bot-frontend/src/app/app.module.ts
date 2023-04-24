import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule }   from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ArticleListComponent } from './component/article-list/article-list.component';
import { ArticleFormComponent } from './component/article-form/article-form.component';
import { ArticleService } from './service/article.service';
import { HttpClientModule } from '@angular/common/http';
import { TestListComponent } from './component/test-list/test-list.component';
import { StatisticsComponent } from './component/statistics/statistics.component';
import { TestFormComponent } from './component/test-form/test-form.component';
import { PaginationModule } from './pagination/pagination/pagination.module';
import { TestService } from './service/test.service';
import { TestStatisticsComponent } from './component/statistics/test-statistics/test-statistics.component';
import { ArticleStatisticsComponent } from './component/statistics/article-statistics/article-statistics.component';
import { AlertModule } from './alert/alert.module';

@NgModule({
  declarations: [
    AppComponent,
    ArticleListComponent,
    ArticleFormComponent,
    TestListComponent,
    StatisticsComponent,
    TestFormComponent,
    TestStatisticsComponent,
    ArticleStatisticsComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    PaginationModule,
    AlertModule
  ],
  providers: [ArticleService, TestService],
  bootstrap: [AppComponent]
})
export class AppModule { }
