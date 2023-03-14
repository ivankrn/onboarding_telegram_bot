import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Article } from 'src/app/model/article';
import { ArticleTopicService } from 'src/app/service/article-topic.service';
import { ArticleService } from 'src/app/service/article.service';

@Component({
  selector: 'app-article-form',
  templateUrl: './article-form.component.html',
  styleUrls: ['./article-form.component.css']
})
export class ArticleFormComponent {

  article: Article;
  articleTopics: Map<number, string>;

  constructor(private route: ActivatedRoute, private router: Router, 
    private articleService: ArticleService, private articleTopicService: ArticleTopicService) {
    this.article = new Article();
    this.updateTopics();
  }

  public onSubmit() {
    this.articleService.save(this.article).subscribe(result => this.goToArticleList());
  }

  public goToArticleList() {
    this.router.navigate(['/articles']);
  }

  updateTopics() {
    this.articleTopicService.getTopicsNames().subscribe(data => {
      this.articleTopics = data;
    });
  }
}
