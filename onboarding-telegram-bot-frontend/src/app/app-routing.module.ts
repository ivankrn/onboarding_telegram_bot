import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArticleFormComponent } from './component/article-form/article-form.component';
import { ArticleListComponent } from './component/article-list/article-list/article-list.component';
import { DashboardComponent } from './component/dashboard/dashboard/dashboard.component';
import { TestFormComponent } from './component/test-form/test-form/test-form.component';
import { TestListComponent } from './component/test-list/test-list/test-list.component';

const routes: Routes = [
  { path: 'articles', component: ArticleListComponent },
  { path: 'article', component: ArticleFormComponent },
  { path: 'tests', component: TestListComponent },
  { path: 'test', component: TestFormComponent },
  { path: '**', component: DashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
