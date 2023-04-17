import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArticleFormComponent } from './component/article-form/article-form.component';
import { ArticleListComponent } from './component/article-list/article-list/article-list.component';
import { StatisticsComponent } from './component/statistics/statistics/statistics.component';
import { TestFormComponent } from './component/test-form/test-form/test-form.component';
import { TestListComponent } from './component/test-list/test-list/test-list.component';

const routes: Routes = [
  { path: 'articles', component: ArticleListComponent },
  { path: 'articles/edit/:id', component: ArticleFormComponent },
  { path: 'articles/create', component: ArticleFormComponent },
  { path: 'tests', component: TestListComponent },
  { path: 'tests/edit/:id', component: TestFormComponent },
  { path: 'tests/create', component: TestFormComponent },
  { path: '**', component: StatisticsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
