import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Article } from 'src/app/model/article';
import { ArticleService } from 'src/app/service/article.service';

@Component({
  selector: 'app-article-form',
  templateUrl: './article-form.component.html',
  styleUrls: ['./article-form.component.css']
})
export class ArticleFormComponent {

  article: Article;

  constructor(private route: ActivatedRoute, private router: Router, private articleService: ArticleService) {
    this.article = new Article();
  }

  public onSubmit() {
    this.articleService.save(this.article).subscribe(result => this.goToArticleList());
  }

  public goToArticleList() {
    this.router.navigate(['/articles']);
  }
}
