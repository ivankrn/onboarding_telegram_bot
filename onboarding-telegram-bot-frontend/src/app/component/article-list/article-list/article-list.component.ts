import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from 'src/app/model/article';
import { ArticleTopicService } from 'src/app/service/article-topic.service';
import { ArticleService } from 'src/app/service/article.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent implements OnInit {

  articlesTotalCount: number;
  articles: Article[];
  articleTopics: Observable<Map<number, string>>;

  constructor(private articleService: ArticleService, private articleTopicService: ArticleTopicService) {}

  ngOnInit() {
    this.updateArticlesTotalCount();
    this.updateList();
    this.updateTopics();
  }

  onDelete(article: Article) {
    this.articleService.delete(article.id).subscribe(() => {
      this.updateArticlesTotalCount();
      this.updateList();
    });
  }

  updateArticlesTotalCount() {
    this.articleService.count().subscribe(data => {
      this.articlesTotalCount = data;
    });
  }

  updateList() {
    this.articleService.findAll().subscribe(data => {
      this.articles = data;
    });
  }

  updateTopics() {
    this.articleTopics = this.articleTopicService.getTopicsNames();
  }

}
