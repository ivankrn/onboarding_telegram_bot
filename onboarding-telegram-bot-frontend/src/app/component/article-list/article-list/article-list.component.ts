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

  articles: Article[];
  articleTopics: Observable<Map<number, string>>;

  constructor(private articleService: ArticleService, private articleTopicService: ArticleTopicService) {}

  ngOnInit(): void {
    this.updateList();
    this.updateTopics();
  }

  onDelete(article: Article) {
    this.articleService.delete(article.id).subscribe(r => this.updateList());
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
