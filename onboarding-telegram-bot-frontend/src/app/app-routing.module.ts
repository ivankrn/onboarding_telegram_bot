import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArticleFormComponent } from './component/article-form/article-form.component';
import { ArticleListComponent } from './component/article-list/article-list/article-list.component';

const routes: Routes = [
  { path: 'articles', component: ArticleListComponent },
  { path: 'addarticle', component: ArticleFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
